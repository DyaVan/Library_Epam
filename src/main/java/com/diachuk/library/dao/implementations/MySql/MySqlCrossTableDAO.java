package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.dao.entities.*;
import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.ICrossTableDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by VA-N_ on 22.01.2017.
 */
public class MySqlCrossTableDAO implements ICrossTableDAO {

    private static MySqlCrossTableDAO instance;

    public static MySqlCrossTableDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlCrossTableDAO();
        }
        return instance;
    }

    private MySqlCrossTableDAO() {
    }

    /**
     * Checks if user with specified {@code userId} has eligible amount of loans and home reservations.
     *
     * @param userId
     * @param bookId
     * @param maxAmount
     * @return Returns true if amount of current user loans and home reservations (except book with specified {@code bookId}
     * is less then specified {@code maxAmount}.
     * @throws SQLException
     */
    public boolean bookIssuingExcessCheck(Integer userId, Integer bookId, Integer maxAmount) throws SQLException {
        if (userId == null || maxAmount == null) {
            return false;
        }
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT COUNT(bookId) AS amount FROM ((SELECT bookId FROM bookloan " +
                             "WHERE userId = ? AND (returnDate IS NULL)) UNION " +
                             "(SELECT bookId FROM homereservation WHERE (userId = ?) AND (bookId <> ?))) AS books")) {

            stm.setInt(1, userId);
            stm.setInt(2, userId);
            stm.setInt(3, bookId);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                if (rs.getInt("amount") < maxAmount) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean createBookLoanFromReservation(Integer reservationId, Integer bookId, Integer userId, String type, Integer loanDuration) throws SQLException {
        Connection connection = MySqlDAOFactory.createConnection();
        if (connection == null) {
            return false;
        }
        try (PreparedStatement decreaseAvailableStm = connection.prepareStatement("UPDATE book SET availableInRRoom = (availableInRRoom - 1) WHERE id = ?");
                PreparedStatement insertBookLoanStm = connection.prepareStatement(
                        "INSERT INTO bookloan (userId, bookId, type, tookDate, dueDate) " +
                                "VALUES (?,?,?,curdate(),(CURDATE() + INTERVAL ? DAY))")) {

            StringBuilder deleteReservationQuery = new StringBuilder("DELETE FROM ");
            switch (type) {
                case MySqlBookLoanDAO.RROOM_TYPE: {
                    deleteReservationQuery.append("rroomreservation ");
                    break;
                }
                case MySqlBookLoanDAO.HOME_TYPE: {
                    deleteReservationQuery.append("homereservation ");
                }
            }
            deleteReservationQuery.append(" WHERE id = " + reservationId);

            decreaseAvailableStm.setInt(1, bookId);
            insertBookLoanStm.setInt(1, userId);
            insertBookLoanStm.setInt(2, bookId);
            insertBookLoanStm.setString(3, type);
            insertBookLoanStm.setInt(4, loanDuration);

            connection.setAutoCommit(false);

            Statement deleteReservationStm = connection.createStatement();
            int result1 = deleteReservationStm.executeUpdate(deleteReservationQuery.toString());
            int result2 = decreaseAvailableStm.executeUpdate();
            int result3 = insertBookLoanStm.executeUpdate();
            if (result1 == 1 && result2 == 1 && result3 == 1) {
                connection.commit();
                return true;
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            //todo: logging
            throw e;
        } finally {
            connection.close();
        }
    }

    public boolean createReservationFromRequest(Request bookRequest, Integer reservationDuration) throws SQLException {
        Connection connection = MySqlDAOFactory.createConnection();
        if (connection == null) {
            return false;
        }
        try (PreparedStatement deleteRequestStm = connection.prepareStatement("DELETE FROM request WHERE request.id = ?");
             PreparedStatement insertReservationStm = connection.prepareStatement(
                     "INSERT INTO homereservation (userId, bookId, dueDate) " +
                             "VALUES (?,?,(CURDATE() + INTERVAL ? DAY))")) {

            deleteRequestStm.setInt(1, bookRequest.getId());
            insertReservationStm.setInt(1, bookRequest.getUserId());
            insertReservationStm.setInt(2, bookRequest.getBookId());
            insertReservationStm.setInt(3, reservationDuration);

            connection.setAutoCommit(false);
            int result1 = deleteRequestStm.executeUpdate();
            int result2 = insertReservationStm.executeUpdate();
            if (result1 == 1 && result2 == 1) {
                connection.commit();
                return true;
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            //todo: logging
            throw e;
        } finally {
            connection.close();
        }
    }

    public ArrayList<BookLoan> getUserHistory(Integer userId) throws SQLException {
        ArrayList<BookLoan> loansHistory = new ArrayList<>();
        if (userId == null) {
            return loansHistory;
        }

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT bookloan.id, bookloan.bookId, bookloan.tookDate, bookloan.type, bookloan.dueDate, bookloan.returnDate, " +
                             "book.name, book.author " +
                             "FROM bookloan JOIN book ON (bookloan.bookId = book.id) WHERE bookloan.userId = ?")) {

            stm.setInt(1, userId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                BookLoan bookLoan = new BookLoan();
                bookLoan.setId(rs.getInt(MySqlBookLoanDAO.ID_COLUMN));
                bookLoan.setBookId(rs.getInt(MySqlBookLoanDAO.BOOK_ID_COLUMN));
                bookLoan.setType(rs.getString(MySqlBookLoanDAO.TYPE_COLUMN));
                bookLoan.setTookDate(rs.getDate(MySqlBookLoanDAO.TOOK_DATE_COLUMN));
                bookLoan.setDueDate(rs.getDate(MySqlBookLoanDAO.DUE_DATE_COLUMN));
                bookLoan.setReturnDate(rs.getDate(MySqlBookLoanDAO.RETURN_DATE_COLUMN));

                Book book = new Book();
                book.setName(rs.getString(MySqlBookDAO.NAME_COLUMN));
                book.setAuthor(rs.getString(MySqlBookDAO.AUTHOR_COLUMN));
                bookLoan.setBook(book);

                loansHistory.add(bookLoan);
            }

            return loansHistory;
        }
    }

    public ArrayList<IReservation> getUserCurrentReservations(Integer userId) throws SQLException {
        ArrayList<IReservation> currentReservaations = new ArrayList<>();
        if (userId == null) {
            return currentReservaations;
        }

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement getHomeReservationsStm = connection.prepareStatement(
                     "SELECT homereservation.id, homereservation.bookId, homereservation.dueDate, book.name, book.author FROM homereservation JOIN book ON (homereservation.bookId = book.id) WHERE homereservation.userId = ?");
             PreparedStatement getRRoomReservationsStm = connection.prepareStatement(
                     "SELECT rroomreservation.id, rroomreservation.bookId, rroomreservation.dueTime, book.name, book.author FROM rroomreservation JOIN book ON (rroomreservation.bookId = book.id) WHERE rroomreservation.userId = ?")) {

            getHomeReservationsStm.setInt(1, userId);
            getRRoomReservationsStm.setInt(1, userId);

            ResultSet homeReservationRs = getHomeReservationsStm.executeQuery();
            ResultSet rRoomReservationsRs = getRRoomReservationsStm.executeQuery();

            while (homeReservationRs.next()) {
                HomeReservation homeReservation = new HomeReservation();
                homeReservation.setId(homeReservationRs.getInt(MySqlHomeReservationDAO.ID_COLUMN));
                homeReservation.setBookId(homeReservationRs.getInt(MySqlHomeReservationDAO.BOOK_ID_COLUMN));
                homeReservation.setDueTo(homeReservationRs.getDate(MySqlHomeReservationDAO.DUE_DATE_COLUMN));
                Book book = new Book();
                book.setName(MySqlBookDAO.NAME_COLUMN);
                book.setAuthor(MySqlBookDAO.AUTHOR_COLUMN);
                homeReservation.setBook(book);
                currentReservaations.add(homeReservation);
            }

            while (rRoomReservationsRs.next()) {
                RRoomReservation rRoomReservation = new RRoomReservation();
                rRoomReservation.setId(homeReservationRs.getInt(MySqlRRoomReservationDAO.ID_COLUMN));
                rRoomReservation.setBookId(homeReservationRs.getInt(MySqlRRoomReservationDAO.BOOK_ID_COLUMN));
                rRoomReservation.setDueTo(homeReservationRs.getDate(MySqlRRoomReservationDAO.DUE_TIME_COLUMN));
                Book book = new Book();
                book.setName(MySqlBookDAO.NAME_COLUMN);
                book.setAuthor(MySqlBookDAO.AUTHOR_COLUMN);
                rRoomReservation.setBook(book);
                currentReservaations.add(rRoomReservation);
            }
            return currentReservaations;
        }
    }

    public ArrayList<Request> getUserCurrentRequests(Integer userId) throws SQLException {
        ArrayList<Request> currentRequests = new ArrayList<>();
        if (userId == null) {
            return currentRequests;
        }

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT request.id, request.bookId, request.requestDate book.name, book.author " +
                             "FROM bookloan JOIN book ON (bookloan.bookId = book.id) WHERE request.userId = ?")) {

            stm.setInt(1, userId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Request request = new Request();
                request.setId(rs.getInt(MySqlRequestDAO.ID_COLUMN));
                request.setBookId(rs.getInt(MySqlRequestDAO.BOOK_ID_COLUMN));
                request.setRequestDate(rs.getDate(MySqlRequestDAO.REQUEST_DATE_COLUMN));
                Book book = new Book();
                book.setName(rs.getString(MySqlBookDAO.NAME_COLUMN));
                book.setAuthor(rs.getString(MySqlBookDAO.AUTHOR_COLUMN));
                currentRequests.add(request);
            }

            return currentRequests;
        }

    }

    public ArrayList<BookLoan> getUserCurrentBookLoans(Integer userId) throws SQLException {
        ArrayList<BookLoan> loansHistory = new ArrayList<>();
        if (userId == null) {
            return loansHistory;
        }

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT bookloan.id, bookloan.bookId, bookloan.tookDate, bookloan.type, bookloan.dueDate, bookloan.returnDate, " +
                             "book.name, book.author " +
                             "FROM bookloan JOIN book ON (bookloan.bookId = book.id) WHERE bookloan.userId = ? AND bookLoan.returnDate IS null")) {

            stm.setInt(1, userId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                BookLoan bookLoan = new BookLoan();
                bookLoan.setId(rs.getInt(MySqlBookLoanDAO.ID_COLUMN));
                bookLoan.setBookId(rs.getInt(MySqlBookLoanDAO.BOOK_ID_COLUMN));
                bookLoan.setType(rs.getString(MySqlBookLoanDAO.TYPE_COLUMN));
                bookLoan.setTookDate(rs.getDate(MySqlBookLoanDAO.TOOK_DATE_COLUMN));
                bookLoan.setDueDate(rs.getDate(MySqlBookLoanDAO.DUE_DATE_COLUMN));
                bookLoan.setReturnDate(rs.getDate(MySqlBookLoanDAO.RETURN_DATE_COLUMN));
                Book book = new Book();
                book.setName(rs.getString(MySqlBookDAO.NAME_COLUMN));
                book.setAuthor(rs.getString(MySqlBookDAO.AUTHOR_COLUMN));
                bookLoan.setBook(book);
                loansHistory.add(bookLoan);
            }

            return loansHistory;
        }
    }

    public BookLoan findBookLoanVSUser(Integer bookLoanId) throws SQLException {
        if (bookLoanId == null) {
            return null;
        }
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("SELECT bookloan.id, bookloan.type, bookloan.bookId, " +
                     "bookloan.tookDate, bookloan.dueDate, book.name AS bookName, book.author AS bookAuthor, " +
                     "user.name as userName, user.surname as userSurname, user.email AS userEmail " +
                     "FROM bookloan JOIN book ON (bookloan.bookId = book.id) JOIN user ON (bookloan.userId = user.id) " +
                     "WHERE bookloan.id = ? AND returnDate IS NULL LIMIT 1")) {

            stm.setInt(1, bookLoanId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                BookLoan bookLoan = new BookLoan();
                bookLoan.setId(rs.getInt(MySqlBookLoanDAO.ID_COLUMN));
                bookLoan.setBookId(rs.getInt(MySqlBookLoanDAO.BOOK_ID_COLUMN));
                bookLoan.setType(rs.getString(MySqlBookLoanDAO.TYPE_COLUMN));
                bookLoan.setTookDate(rs.getDate(MySqlBookLoanDAO.TOOK_DATE_COLUMN));
                bookLoan.setDueDate(rs.getDate(MySqlBookLoanDAO.DUE_DATE_COLUMN));

                Book book = new Book();
                book.setName(rs.getString("bookName"));
                book.setAuthor(rs.getString("bookAuthor"));
                bookLoan.setBook(book);

                User user = new User();
                user.setName(rs.getString("userName"));
                user.setSurname(rs.getString("userSurname"));
                user.setEmail(rs.getString("userEmail"));
                bookLoan.setUser(user);

                return bookLoan;
            }
            return null;
        }
    }

    public ArrayList<BookLoan> findNotReturnedOverdueBookLoans() throws SQLException {
        ArrayList<BookLoan> overdueBookLoans = new ArrayList<>();
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("SELECT bookloan.id, bookloan.type, bookloan.bookId, " +
                     "bookloan.userId, bookloan.tookDate, bookloan.dueDate, book.name AS bookName, book.author AS bookAuthor, " +
                     "user.name as userName, user.surname as userSurname, user.email AS userEmail " +
                     "FROM bookloan JOIN book ON (bookloan.bookId = book.id) JOIN user ON (bookloan.userId = user.id) " +
                     "WHERE bookloan.returnDate = IS NULL AND bookloan.dueDate < NOW()")) {

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                BookLoan bookLoan = new BookLoan();
                bookLoan.setId(rs.getInt(MySqlBookLoanDAO.ID_COLUMN));
                bookLoan.setBookId(rs.getInt(MySqlBookLoanDAO.BOOK_ID_COLUMN));
                bookLoan.setUserId(rs.getInt(MySqlBookLoanDAO.USER_ID_COLUMN));
                bookLoan.setType(rs.getString(MySqlBookLoanDAO.TYPE_COLUMN));
                bookLoan.setTookDate(rs.getDate(MySqlBookLoanDAO.TOOK_DATE_COLUMN));
                bookLoan.setDueDate(rs.getDate(MySqlBookLoanDAO.DUE_DATE_COLUMN));

                Book book = new Book();
                book.setName(rs.getString(MySqlBookDAO.NAME_COLUMN));
                book.setAuthor(rs.getString(MySqlBookDAO.AUTHOR_COLUMN));
                bookLoan.setBook(book);

                User user = new User();
                user.setId(rs.getInt(MySqlBookLoanDAO.USER_ID_COLUMN));
                user.setName(rs.getString("userName"));
                user.setSurname(rs.getString("userSurname"));
                user.setEmail(rs.getString("userEmail"));
                bookLoan.setUser(user);

                overdueBookLoans.add(bookLoan);
            }
            return overdueBookLoans;
        }
    }

    public boolean insertRRoomReservation(Book book, User user, Date dueTime) throws SQLException {
        if (book == null || book.getAvailableInRRoom() <= 0 || user == null || dueTime == null || dueTime.compareTo(new Date()) <= 0) {
            return false;
        }

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stmForBookTable =connection.prepareStatement(
                     "UPDATE book SET book.availableInRRoom = (book.availableInRRoom - 1) WHERE (book.id = ?) " +
                             "AND (book.availableInRRoom > 0)");
             PreparedStatement stmForReservationTable = connection.prepareStatement(
                     "INSERT INTO rroomreservation (rroomreservation.bookId, rroomreservation.userId, rroomreservation.dueTime) VALUES (?,?,?)"
             )) {

            stmForBookTable.setInt(1,book.getId());

            stmForReservationTable.setInt(1,book.getId());
            stmForReservationTable.setInt(2,user.getId());
            stmForReservationTable.setDate(3,new java.sql.Date(dueTime.getTime()));

            connection.setAutoCommit(false);
            boolean error = false;
            try {
                if (stmForBookTable.executeUpdate() != 1) {
                    error = true;
                }
                if (stmForReservationTable.executeUpdate() != 1) {
                    error = true;
                }
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
            if (error) {
                connection.rollback();
                return false;
            }
            connection.commit();
            return true;
        }


    }

    public boolean insertHomeReservation(Book book, User user, Date dueDate) throws SQLException {
        if (book == null || book.getAvailableForHome() <= 0 || user == null || dueDate == null || dueDate.compareTo(new Date()) <= 0) {
            return false;
        }

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stmForBookTable =connection.prepareStatement(
                     "UPDATE book SET book.availableForHome = (book.availableForHome - 1) WHERE (book.id = ?) " +
                             "AND (book.availableForHome > 0)");
             PreparedStatement stmForReservationTable = connection.prepareStatement(
                     "INSERT INTO homereservation (homereservation.bookId, homereservation.userId, homereservation.dueDate) VALUES (?,?,?)"
             )) {

            stmForBookTable.setInt(1,book.getId());

            stmForReservationTable.setInt(1,book.getId());
            stmForReservationTable.setInt(2,user.getId());
            stmForReservationTable.setDate(3,new java.sql.Date(dueDate.getTime()));

            connection.setAutoCommit(false);
            boolean error = false;
            try {
                if (stmForBookTable.executeUpdate() != 1) {
                    error = true;
                }
                if (stmForReservationTable.executeUpdate() != 1) {
                    error = true;
                }
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
            if (error) {
                connection.rollback();
                return false;
            }
            connection.commit();
            return true;
        }


    }

}
