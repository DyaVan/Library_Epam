package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.dao.entities.Book;
import com.diachuk.library.dao.entities.Request;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.IRequestDAO;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.sql.*;
import java.util.Collection;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by VA-N_ on 29.12.2016.
 */
public class MySqlRequestDAO implements IRequestDAO {

    final static String ID_COLUMN = "id";
    final static String BOOK_ID_COLUMN = "bookId";
    final static String USER_ID_COLUMN = "userId";
    final static String REQUEST_DATE_COLUMN = "requestDate";


    private static MySqlRequestDAO instance;

    public static MySqlRequestDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlRequestDAO();
        }
        return instance;
    }

    private MySqlRequestDAO() {

    }

    /**
     * Saves a new request to the DB.
     *
     * @param book
     * @param user
     * @return true if successful, otherwise - false.
     * @throws SQLException
     */
    public boolean insertRequest(Book book, User user) throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "INSERT INTO request (request.bookId, request.userId, request.requestDate) VALUES (?,?,current_time())")) {

            stm.setInt(1, book.getId());
            stm.setInt(2, user.getId());

            if (stm.executeUpdate() > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();//// TODO: 20.01.2017 logging
            throw e;
        }
    }

    /**
     * Checks is there already such request for the book from user.
     *
     * @param bookId
     * @param user
     * @return true if request for the specified book from specified user exists, otherwise - false.
     * @throws SQLException
     */
    public boolean isCreatedByBookAndUser(Integer bookId, User user) throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT request.id FROM request WHERE (request.bookId = ?) AND (request.userId = ?) LIMIT 1")) {

            stm.setInt(1, bookId);
            stm.setInt(2, user.getId());

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();//// TODO: 20.01.2017 logging
            throw e;
        }
    }

    public Request getOldestByBook(Integer bookId, Integer offset) throws SQLException {

        if (bookId == null || offset == null) {
            return null;
        }

        try (Connection connection = MySqlDAOFactory.createConnection()) {

            String query = String.format(
                    "SELECT request.id, request.bookId, request.userId, request.requestDate " +
                            "FROM request WHERE request.bookId = %1d LIMIT %2d , 1 ", bookId, offset);

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);

            if (rs.next()) {
                Request request = new Request();
                request.setId(rs.getInt(ID_COLUMN));
                request.setBookId(rs.getInt(BOOK_ID_COLUMN));
                request.setUserId(rs.getInt(USER_ID_COLUMN));
                request.setRequestDate(rs.getDate(REQUEST_DATE_COLUMN));
                return request;
            }
        }
        return null;
    }

}
