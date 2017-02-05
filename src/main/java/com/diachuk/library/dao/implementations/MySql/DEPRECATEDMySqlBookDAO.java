package com.diachuk.library.dao.implementations.MySql;



import com.diachuk.library.dao.entities.Book;
import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.IBookDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import static com.diachuk.library.dao.implementations.MySql.DEPRECATEDMySqlBookDAO.Columns.*;


/**
 * Created by VA-N_ on 15.01.2017.
 */
public class DEPRECATEDMySqlBookDAO implements IBookDAO {

    //temporary testing method
    public static String check() {
        List<Book> b = new DEPRECATEDMySqlBookDAO().findByName("Harry");
        LinkedHashMap lhm = new LinkedHashMap();
        lhm.put(DEPRECATEDMySqlBookDAO.Columns.name, "Harryss");
//        lhm.put(Columns.criticsRate, 50);
        lhm.put(DEPRECATEDMySqlBookDAO.Columns.readersRate, 50);
        getInstance().updateDynamic(2, lhm);
        return getInstance().findByName("Harryss").get(0).toString();
    }

    private static DEPRECATEDMySqlBookDAO instance;

    public static DEPRECATEDMySqlBookDAO getInstance() {
        if (instance == null) {
            return instance = new DEPRECATEDMySqlBookDAO();
        }
        return instance;
    }

    //// TODO: 28.12.2016 implement findbyId !!!

    private DEPRECATEDMySqlBookDAO() {

    }

    enum Columns {
        id, name, author,year, readersRate, criticsRate, popularity, description, pages, amountForHome, availableForHome, amountInRRoom, availableInRRoom;
        private int rqstPosition;

        public int getRequestPosition() {
            return rqstPosition;
        }

        public void setRequestPosition(int requestPosition) {
            this.rqstPosition = requestPosition;
        }
    }

    public ArrayList<Book> findAll() {
        ArrayList books = new ArrayList();

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("SELECT * FROM book")
        ) {
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.id.name()));
                book.setName(rs.getString(name.name()));
                book.setAuthor(rs.getString(DEPRECATEDMySqlBookDAO.Columns.author.name()));
                book.setReadersRate(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.readersRate.name()));
                book.setDescription(rs.getString(DEPRECATEDMySqlBookDAO.Columns.description.name()));
                book.setPages(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.pages.name()));
                book.setAmountForHome(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.amountForHome.name()));
                book.setAvailableForHome(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.availableForHome.name()));
                book.setAmountInRRoom(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.amountInRRoom.name()));
                book.setAvailableInRRoom(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.availableInRRoom.name()));
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public ArrayList<Book> findAllShort() {
        ArrayList books = new ArrayList();

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT book.id, book.author, book.name, book.readersRate, book.criticsRate, book.popularity, " +
                             "book.availableForHome FROM book")
        ) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.id.name()));
                book.setName(rs.getString(name.name()));
                book.setAuthor(rs.getString(DEPRECATEDMySqlBookDAO.Columns.author.name()));
                book.setReadersRate(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.readersRate.name()));
                book.setAvailableForHome(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.availableForHome.name()));
                books.add(book);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public ArrayList<Book> findByName(String bookName) {
        ArrayList books = new ArrayList();

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("SELECT * FROM book WHERE name = ?")
        ) {

            name.setRequestPosition(1);

            stm.setString(name.getRequestPosition(), bookName);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.id.name()));
                book.setName(rs.getString(name.name()));
                book.setAuthor(rs.getString(DEPRECATEDMySqlBookDAO.Columns.author.name()));
                book.setReadersRate(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.readersRate.name()));
                book.setDescription(rs.getString(DEPRECATEDMySqlBookDAO.Columns.description.name()));
                book.setPages(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.pages.name()));
                book.setAmountForHome(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.amountForHome.name()));
                book.setAvailableForHome(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.availableForHome.name()));
                book.setAmountInRRoom(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.amountInRRoom.name()));
                book.setAvailableInRRoom(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.availableInRRoom.name()));
                books.add(book);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public ArrayList<Book> findByNameShort(String bookName) {
        ArrayList books = new ArrayList();

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT book.id, book.author, book.name, book.readersRate, book.criticsRate, book.popularity, " +
                             "book.availableForHome FROM book WHERE name = ?")) {

            name.setRequestPosition(1);

            stm.setString(name.getRequestPosition(), bookName);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.id.name()));
                book.setName(rs.getString(name.name()));
                book.setAuthor(rs.getString(DEPRECATEDMySqlBookDAO.Columns.author.name()));
                book.setReadersRate(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.readersRate.name()));
                book.setAvailableForHome(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.availableForHome.name()));
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public boolean deleteById(Integer id) {
        if (Objects.isNull(id)) {
            return false;
        }

        boolean result = false;

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("DELETE FROM book WHERE id = ?")) {

            DEPRECATEDMySqlBookDAO.Columns.id.setRequestPosition(1);

            stm.setInt(DEPRECATEDMySqlBookDAO.Columns.id.getRequestPosition(), id);

            result = stm.executeUpdate() > 0;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

//    public boolean insert(Book book) {
//        if (Objects.isNull(book)) {
//            return false;
//        }
//
//        boolean result = false;
//
//        try (Connection connection = MySqlDAOFactory.createConnection();
//             PreparedStatement stm = connection.prepareStatement(
//                     "INSERT INTO book (name, author, readersRate, criticsRate, popularity, description, " +
//                             "pages, amount, availableForHome)  values (?,?,?,?,?,?,?,?,?)")
//        ) {
//            name.setRequestPosition(1);
//            author.setRequestPosition(2);
//            readersRate.setRequestPosition(3);
//            criticsRate.setRequestPosition(4);
//            popularity.setRequestPosition(5);
//            description.setRequestPosition(6);
//            pages.setRequestPosition(7);
//            amount.setRequestPosition(8);
//            availableAmount.setRequestPosition(9);
//
//            stm.setString(name.getRequestPosition(), book.getName());
//            stm.setString(author.getRequestPosition(), book.getAuthor());
//            stm.setInt(readersRate.getRequestPosition(), book.getReadersRate());
//            stm.setInt(criticsRate.getRequestPosition(), book.getCriticsRate());
//            stm.setInt(popularity.getRequestPosition(), book.getPopularity());
//            stm.setString(description.getRequestPosition(), book.getDescription());
//            stm.setInt(pages.getRequestPosition(), book.getPages());
//            stm.setInt(amount.getRequestPosition(), book.getAmountForHome());
//            stm.setInt(availableAmount.getRequestPosition(), book.getAvailableForHome());
//
//            result = stm.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    public boolean updateDynamic(Integer bookId, LinkedHashMap<DEPRECATEDMySqlBookDAO.Columns, Object> newValues) {
        if (Objects.isNull(bookId) && newValues.size() <= 1) {
            return false;
        }

        boolean result = false;

        try (Connection connection = MySqlDAOFactory.createConnection()) {
            StringBuilder query = new StringBuilder("UPDATE book SET ");

            for (DEPRECATEDMySqlBookDAO.Columns column : newValues.keySet()) {
                query.append(column.name() + " = ?, ");
            }
            query.deleteCharAt(query.lastIndexOf(","));
            query.append("WHERE id = " + bookId);

            PreparedStatement stm = connection.prepareStatement(query.toString());

            int counter = 1;
            for (Object value : newValues.values()) {
                stm.setObject(counter, value);
                counter++;
            }

            result = stm.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

//    public ArrayList<Book> findByKeyWordsFullText(ArrayList<String> keyWords) throws SQLException {
//        ArrayList<Book> books = new ArrayList<>();
//
//        if (keyWords.isEmpty()) {
//            return books;
//        }
//
//        try (Connection connection = MySqlDAOFactory.createConnection()){
//
//            StringBuilder query  = new StringBuilder("(");
//
//            for (String word : keyWords) {
//                query.append("SELECT book.id, book.name, book.author, book.genre, book.year, book.amountForHome, " +
//                        "book.availableForHome, book.amountInRRoom, book.availableInRRoom, book.readersRate, " +
//                        "book.numberOfVotes, vote.mark " +
//                        "FROM book " +
//                        "LEFT JOIN vote ON (book.id = vote.bookId) WHERE MATCH(name, author) AGAINST (\"" + word + "\") ) UNION (");
//            }
//
//            query.delete(query.lastIndexOf("U"), query.length());
//
//            //todo: limit
////            query.append(" LIMIT 10");
//
//            Statement stm = connection.createStatement();
//            ResultSet rs = stm.executeQuery(query.toString());
//
//            while (rs.next()) {
//                Book book = new Book();
//                book.setId(rs.getInt(ID_COLUMN));
//                book.setName(rs.getString(NAME_COLUMN));
//                book.setAuthor(rs.getString(AUTHOR_COLUMN));
//                book.setGenre(rs.getString(GENRE_COLUMN));
//                book.setYear(rs.getInt(YEAR_COLUMN));
//                book.setReadersRate(rs.getInt(READERS_RATE_COLUMN));
//                book.setNumberOfVotes(rs.getInt(NUMBER_OF_VOTES_COLUMN));
//                book.setAmountForHome(rs.getInt(AMOUNT_FOR_HOME_COLUMN));
//                book.setAvailableForHome(rs.getInt(AVAILABLE_FOR_HOME_COLUMN));
//                book.setAmountInRRoom(rs.getInt(AMOUNT_IN_RROOM_COLUMN));
//                book.setAvailableInRRoom(rs.getInt(AVAILABLE_IN_RROOM_COLUMN));
//                book.setCurrentUserVote(rs.getInt(CURRENT_USER_MARK_COLUMN));
//                books.add(book);
//            }
//
//            stm.close();
//
//        }
//        return books;
//    }

    public ArrayList<Book> findByKeyWords(ArrayList<String> keyWords) {
        ArrayList<Book> books = new ArrayList<>();

        try (Connection connection = MySqlDAOFactory.createConnection()) {
            StringBuilder query =
                    new StringBuilder("SELECT book.id, book.name, book.author, book.year, book.amountForHome, book.availableForHome, book.amountInRRoom, book.availableInRRoom, " +
                            "book.readersRate, book.criticsRate, book.popularity FROM book WHERE ");

            for (String keyWord : keyWords) {
                query.append(String.format("(CONCAT(book.name, book.author) LIKE '%%%1s%%') OR ", keyWord));
            }
            query.delete(query.length() - 3, query.length());

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query.toString());

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.id.name()));
                book.setName(rs.getString(name.name()));
                book.setAuthor(rs.getString(DEPRECATEDMySqlBookDAO.Columns.author.name()));
                book.setYear(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.year.name()));
                book.setReadersRate(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.readersRate.name()));
                book.setAmountForHome(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.amountForHome.name()));
                book.setAvailableForHome(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.availableForHome.name()));
                book.setAmountInRRoom(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.amountInRRoom.name()));
                book.setAvailableInRRoom(rs.getInt(DEPRECATEDMySqlBookDAO.Columns.availableInRRoom.name()));
                books.add(book);
            }

            stm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

//    public static void main(String[] args) {
//        ArrayList<String> keyWords = new ArrayList<>();
//        keyWords.add("word1");
//        keyWords.add("word2");
//        keyWords.add("word3");
//
//        StringBuilder query =
//                new StringBuilder("SELECT book.name, book.author, book.year, book.availableForHome, book.availableInRRoom " +
//                        "FROM book WHERE ");
//
//        for (String keyWord : keyWords) {
//            query.append(String.format("(CONCAT(book.name, book.author) LIKE '%%%1s%%') OR ", keyWord));
//        }
//        query.delete(query.length() - 3, query.length());
//
//        System.out.println(query);
//
//        System.out.println(String.format("(CONCAT(book.name, book.author) LIKE '%%%1s%%'", "lol"));
//    }
}
