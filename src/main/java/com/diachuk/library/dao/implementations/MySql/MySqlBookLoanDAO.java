package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.dao.entities.BookLoan;
import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.IBookLoanDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by VA-N_ on 18.12.2016.
 */
public class MySqlBookLoanDAO implements IBookLoanDAO {

    static final String ID_COLUMN = "id";
    static final String BOOK_ID_COLUMN = "bookId";
    static final String USER_ID_COLUMN = "userId";
    static final String TYPE_COLUMN = "type";
    static final String TOOK_DATE_COLUMN = "tookDate";
    static final String DUE_DATE_COLUMN = "dueDate";
    static final String RETURN_DATE_COLUMN = "returnDate";

    static final String HOME_TYPE = "Home";
    static final String RROOM_TYPE = "RRoom";


    private static MySqlBookLoanDAO instance;

    public static MySqlBookLoanDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlBookLoanDAO();
        }
        return instance;
    }

    private MySqlBookLoanDAO(){

    }

    /**
     * Checks if there is not returned book loan of book with specified {@code bookId} by user with specified {@code userId}
     * @param bookId
     * @param userId
     * @return
     * @throws SQLException
     */
    public boolean checkExistenceByBookVSUser(Integer bookId, Integer userId) throws SQLException {
        if (bookId == null || userId == null) {
            return false;
        }
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT bookloan.id FROM bookloan WHERE (bookloan.userid = ?) " +
                             "AND (bookloan.bookId = ?) AND bookloan.returnDate IS NULL LIMIT 1")) {

            stm.setInt(1, userId);
            stm.setInt(2, bookId);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }

    public BookLoan findBookLoanById(Integer bookLoanId) throws SQLException {
        if (bookLoanId == null ) {
            return null;
        }
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT bookloan.id, bookloan.userId, bookloan.bookId, bookloan.type, bookloan.tookDate, " +
                             "bookloan.dueDate, bookloan.returnDate " +
                             "FROM bookloan WHERE bookloan.id = ? LIMIT 1")) {

            stm.setInt(1, bookLoanId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                BookLoan bookLoan = new BookLoan();
                bookLoan.setId(rs.getInt(ID_COLUMN));
                bookLoan.setBookId(rs.getInt(BOOK_ID_COLUMN));
                bookLoan.setUserId(rs.getInt(USER_ID_COLUMN));
                bookLoan.setType(rs.getString(TYPE_COLUMN));
                bookLoan.setTookDate(rs.getDate(TOOK_DATE_COLUMN));
                bookLoan.setDueDate(rs.getDate(DUE_DATE_COLUMN));
                bookLoan.setReturnDate(rs.getDate(RETURN_DATE_COLUMN));
                return bookLoan;
            }
        }
        return null;
    }

    public boolean setReturnDate(Integer bookLoanId) throws SQLException {
        if (bookLoanId == null) {
            return false;
        }

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "UPDATE bookloan SET bookloan.returnDate = CURRENT_DATE() WHERE bookloan.id = ?")){

            stm.setInt(1, bookLoanId);

            if (stm.executeUpdate() > 0) {
                return true;
            }
            return false;
        }

    }
}
