package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.dao.entities.Book;
import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.IBookDAO;

import java.sql.*;
import java.util.*;


/**
 * Created by VA-N_ on 01.12.2016.
 */
public class MySqlBookDAO implements IBookDAO {

    static final String ID_COLUMN = "id";
    static final String NAME_COLUMN = "name";
    static final String AUTHOR_COLUMN = "author";
    static final String GENRE_COLUMN = "genre";
    static final String YEAR_COLUMN = "year";
    static final String READERS_RATE_COLUMN = "readersRate";
    static final String NUMBER_OF_VOTES_COLUMN = "numberOfVotes";
    static final String DESCRIPTION_COLUMN = "description";
    static final String PAGES_COLUMN = "pages";
    static final String AMOUNT_FOR_HOME_COLUMN = "amountForHome";
    static final String AVAILABLE_FOR_HOME_COLUMN = "availableForHome";
    static final String AMOUNT_IN_RROOM_COLUMN = "amountInRRoom";
    static final String AVAILABLE_IN_RROOM_COLUMN = "availableInRRoom";
    /**
     * Column joined from vote table.
     */
    private static final String CURRENT_USER_MARK_COLUMN = "mark";


    private static MySqlBookDAO instance;

    public static MySqlBookDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlBookDAO();
        }
        return instance;
    }

    private MySqlBookDAO() {

    }

    public ArrayList<Book> findAllBooks(String genreFilter, Integer offset) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();

        Integer validOffset = offset;
        if (validOffset == null || validOffset < 0) {
            validOffset = 0;
        }

        try (Connection connection = MySqlDAOFactory.createConnection()) {
            StringBuilder query =
                    new StringBuilder("SELECT book.id, book.name, book.author, book.genre, book.year, book.amountForHome, " +
                            "book.availableForHome, book.amountInRRoom, book.availableInRRoom, book.readersRate, " +
                            "book.numberOfVotes, vote.mark " +
                            "FROM book LEFT JOIN vote ON (book.id = vote.bookId) WHERE book.id > 0 ");

            if (genreFilter != null && !genreFilter.isEmpty()) {
                query.append(String.format("AND genre = \"%1s\" ", genreFilter));
            }
            query.append("ORDER BY name");
            query.append(String.format(" LIMIT %1d , 10", validOffset * 10));

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query.toString());

            while (rs.next()) {
                Book book = shortFill_BookFromResultSet(rs);
                books.add(book);
            }

            stm.close();

        }
        return books;
    }

    private Integer controllOffsetValue(Integer offset) {
        return (offset == null || offset < 0) ? 0 : offset;
    }

    private String limitInsertion(Integer validOffset) {
        return String.format(" LIMIT %1d , 10", validOffset * 10);
    }

    public ArrayList<Book> findBySearchLineFullText(String searchLine, String genreFilter, Integer offset) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();

        if (searchLine.isEmpty()) {
            return books;
        }

        Integer validOffset = controllOffsetValue(offset);

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT book.id, book.name, book.author, " +
                     "book.genre, book.year, book.amountForHome, book.availableForHome, book.amountInRRoom, " +
                     "book.availableInRRoom, book.readersRate, book.numberOfVotes, vote.mark " +
                     "FROM book LEFT JOIN vote ON (book.id = vote.bookId) WHERE MATCH(name, author) " +
                     "AGAINST (?)  AND genre LIKE ? " + limitInsertion(validOffset))) {

            preparedStatement.setString(1, searchLine);
            preparedStatement.setString(2,"%" + genreFilter + "%");

//            StringBuilder query = new StringBuilder("SELECT book.id, book.name, book.author, book.genre, book.year, book.amountForHome, " +
//                    "book.availableForHome, book.amountInRRoom, book.availableInRRoom, book.readersRate, " +
//                    "book.numberOfVotes, vote.mark " +
//                    "FROM book " +
//                    "LEFT JOIN vote ON (book.id = vote.bookId) WHERE ( MATCH(name, author) AGAINST ");
//
//
//            query.append(" (\"" + searchLine + "\")) ");
//
//            if (genreFilter != null && !genreFilter.isEmpty()) {
//                query.append(String.format("AND genre = \"%1s\" ", genreFilter));
//            }
//            query.append(String.format(" LIMIT %1d , 10", validOffset * 10));
//
//            Statement stm = connection.createStatement();
//            ResultSet rs = stm.executeQuery(query.toString());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Book book = shortFill_BookFromResultSet(rs);
                books.add(book);
            }

//            stm.close();

        }
        return books;
    }

    public ArrayList<Book> findByKeyWordsSimple(ArrayList<String> keyWords, String genreFilter, Integer offset) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();

        if (keyWords.isEmpty()) {
            return books;
        }

        Integer validOffset = offset;
        if (validOffset == null || validOffset < 0) {
            validOffset = 0;
        }

        try (Connection connection = MySqlDAOFactory.createConnection()) {
            StringBuilder query =
                    new StringBuilder("SELECT book.id, book.name, book.author, book.genre, book.year, book.amountForHome, " +
                            "book.availableForHome, book.amountInRRoom, book.availableInRRoom, book.readersRate, " +
                            "book.numberOfVotes, vote.mark " +
                            "FROM book LEFT JOIN vote ON (book.id = vote.bookId) WHERE (");

            for (String keyWord : keyWords) {
                query.append(String.format("(CONCAT(name, author) LIKE '%%%1s%%') OR ", keyWord));
            }
            query.delete(query.length() - 3, query.length());

            query.append(" ) ");

            if (genreFilter != null && !genreFilter.isEmpty()) {
                query.append(String.format("AND genre = \"%1s\" ", genreFilter));
            }
            query.append(String.format(" LIMIT %1d , 10", validOffset * 10));

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query.toString());

            while (rs.next()) {
                Book book = shortFill_BookFromResultSet(rs);
                books.add(book);
            }

            stm.close();

        }
        return books;
    }

    public Book findBookById(Integer bookId) throws SQLException {
        if (bookId == null) {
            return null;
        }
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("SELECT book.id, book.name, book.author, book.genre, " +
                     "book.year, book.readersRate, book.numberOfVotes, book.description, book.pages, book.amountForHome, " +
                     "book.availableForHome, book.amountInRRoom, book.availableInRRoom, vote.mark " +
                     "FROM book LEFT JOIN vote ON (book.id = vote.bookId) WHERE book.id = ? LIMIT 1")) {

            stm.setInt(1, bookId);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Book book = fullFill_BookFromResultSet(rs);
                return book;
            }
        }
        return null;
    }

    public int getAvailableForHomeByBookId(Integer bookId) throws SQLException {
        if (bookId == null) {
            return 0;
        }
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("SELECT book.availableForHome " +
                     "FROM book WHERE book.id = ? LIMIT 1")) {

            stm.setInt(1, bookId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(AVAILABLE_FOR_HOME_COLUMN);
            }
        }
        return 0;
    }

    public int getAvailableInRRoomByBookId(Integer bookId) throws SQLException {
        if (bookId == null) {
            return 0;
        }
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("SELECT book.availableInRRoom " +
                     "FROM book WHERE book.id = ? LIMIT 1")) {

            stm.setInt(1, bookId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(AVAILABLE_IN_RROOM_COLUMN);
            }
        }
        return 0;
    }

    public boolean increaseAvailableForHome(Integer bookId) throws SQLException {
        if (bookId == null) {
            return false;
        }

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "UPDATE book SET availableForHome = (availableForHome + 1) WHERE book.id = ?")) {

            stm.setInt(1, bookId);

            if (stm.executeUpdate() > 0) {
                return true;
            }
            return false;
        }
    }

    public ArrayList<Book> findTop10(String genreFilter) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();

        try (Connection connection = MySqlDAOFactory.createConnection()) {

            StringBuilder query = new StringBuilder("SELECT book.id, book.name, book.author, book.genre, book.year, " +
                    "book.amountForHome, book.availableForHome, book.amountInRRoom, book.availableInRRoom, " +
                    "book.readersRate, book.numberOfVotes, vote.mark FROM book LEFT JOIN vote ON (book.id = vote.bookId)");

            if (genreFilter != null && !genreFilter.isEmpty()) {
                query.append(" WHERE book.genre = " + genreFilter);
            }

            query.append(" ORDER BY book.readersRate LIMIT 10");

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query.toString());

            while (rs.next()) {
                Book book = shortFill_BookFromResultSet(rs);
                books.add(book);
            }

        }
        return books;
    }

    private Book shortFill_BookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt(ID_COLUMN));
        book.setName(rs.getString(NAME_COLUMN));
        book.setAuthor(rs.getString(AUTHOR_COLUMN));
        book.setGenre(rs.getString(GENRE_COLUMN));
        book.setYear(rs.getInt(YEAR_COLUMN));
        book.setReadersRate(rs.getInt(READERS_RATE_COLUMN));
        book.setNumberOfVotes(rs.getInt(NUMBER_OF_VOTES_COLUMN));
        book.setAmountForHome(rs.getInt(AMOUNT_FOR_HOME_COLUMN));
        book.setAvailableForHome(rs.getInt(AVAILABLE_FOR_HOME_COLUMN));
        book.setAmountInRRoom(rs.getInt(AMOUNT_IN_RROOM_COLUMN));
        book.setAvailableInRRoom(rs.getInt(AVAILABLE_IN_RROOM_COLUMN));
        book.setCurrentUserVote(rs.getInt(CURRENT_USER_MARK_COLUMN));
        return book;
    }

    private Book fullFill_BookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt(ID_COLUMN));
        book.setName(rs.getString(NAME_COLUMN));
        book.setAuthor(rs.getString(AUTHOR_COLUMN));
        book.setGenre(rs.getString(GENRE_COLUMN));
        book.setYear(rs.getInt(YEAR_COLUMN));
        book.setPages(rs.getInt(PAGES_COLUMN));
        book.setReadersRate(rs.getInt(READERS_RATE_COLUMN));
        book.setNumberOfVotes(rs.getInt(NUMBER_OF_VOTES_COLUMN));
        book.setAmountForHome(rs.getInt(AMOUNT_FOR_HOME_COLUMN));
        book.setAvailableForHome(rs.getInt(AVAILABLE_FOR_HOME_COLUMN));
        book.setAmountInRRoom(rs.getInt(AMOUNT_IN_RROOM_COLUMN));
        book.setAvailableInRRoom(rs.getInt(AVAILABLE_IN_RROOM_COLUMN));
        book.setDescription(rs.getString(DESCRIPTION_COLUMN));
        book.setCurrentUserVote(rs.getInt(CURRENT_USER_MARK_COLUMN));
        return book;
    }

    public ArrayList<Book> getBooksRead(Integer userId, String genreFilter, Integer offset) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();

        Integer validOffset = offset;
        if (validOffset == null || validOffset < 0) {
            validOffset = 0;
        }

        try (Connection connection = MySqlDAOFactory.createConnection()) {

            StringBuilder query = new StringBuilder("SELECT book.id, book.name, book.author, book.genre, book.year, book.amountForHome, " +
                    "book.availableForHome, book.amountInRRoom, book.availableInRRoom, book.readersRate, " +
                    "book.numberOfVotes, vote.mark " +
                    "FROM book " +
                    "LEFT JOIN vote ON (book.id = vote.bookId) WHERE vote.userId =  ");


            query.append(userId);

            if (genreFilter != null && !genreFilter.isEmpty()) {
                query.append(String.format(" AND genre = \"%1s\" ", genreFilter));
            }

            query.append(String.format(" LIMIT %1d , 10", validOffset));

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query.toString());

            while (rs.next()) {
                Book book = shortFill_BookFromResultSet(rs);
                books.add(book);
            }

            stm.close();

        }
        return books;
    }

    public ArrayList<Book> getBooksToRead(Integer userId, String genreFilter, Integer offset) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();

        Integer validOffset = offset;
        if (validOffset == null || validOffset < 0) {
            validOffset = 0;
        }

        try (Connection connection = MySqlDAOFactory.createConnection()) {

            StringBuilder query = new StringBuilder("SELECT book.id, book.name, book.author, book.genre, book.year, book.amountForHome, " +
                    "book.availableForHome, book.amountInRRoom, book.availableInRRoom, book.readersRate, " +
                    "book.numberOfVotes, vote.mark " +
                    "FROM book " +
                    "LEFT JOIN vote ON (book.id = vote.bookId) JOIN futurebook ON (book.id = futurebook.bookId) WHERE futurebook.userId =  ");


            query.append(userId);

            if (genreFilter != null && !genreFilter.isEmpty()) {
                query.append(String.format(" AND genre = \"%1s\" ", genreFilter));
            }

            query.append(String.format(" LIMIT %1d , 10", validOffset));

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query.toString());

            while (rs.next()) {
                Book book = shortFill_BookFromResultSet(rs);
                books.add(book);
            }

            stm.close();

        }
        return books;
    }


    //todo add catch everywhere to make logging
    public boolean insertBook(String bookName, String author, Integer year, String genre, String description,
                              Integer pages, Integer amountForHome, Integer amountInRRoom) throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("insert into book (name,author, year, genre, pages, " +
                     "description, amountForHome, availableForHome,amountInRRoom,availableInRRoom) " +
                     "values (?,?,?,?,?,?,?,?,?,?)")) {

            stm.setString(1, bookName);
            stm.setString(2, author);
            stm.setInt(3, year);
            stm.setString(4, genre);
            stm.setInt(5, pages);
            stm.setString(6, description);
            stm.setInt(7, amountForHome);
            stm.setInt(8, amountForHome);
            stm.setInt(9, amountInRRoom);
            stm.setInt(10, amountInRRoom);

            if (stm.executeUpdate() > 0) {
                return true;
            }
            return false;
        }

    }

    public Integer findBookIdByNameAuthorYear(String bookName, String author, Integer year) throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT id FROM book WHERE book.name = ? AND book.author = ? AND book.year = ? ")) {

            stm.setString(1, bookName);
            stm.setString(2, author);
            stm.setInt(3, year);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else return -1;
        }
    }
}
