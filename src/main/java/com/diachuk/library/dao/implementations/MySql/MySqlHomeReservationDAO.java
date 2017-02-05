package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.dao.entities.Book;
import com.diachuk.library.dao.entities.HomeReservation;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.IHomeReservationDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by VA-N_ on 18.12.2016.
 */
public class MySqlHomeReservationDAO implements IHomeReservationDAO {

     final static String ID_COLUMN = "id";
     final static String BOOK_ID_COLUMN = "bookId";
     final static String USER_ID_COLUMN = "userId";
     final static String DUE_DATE_COLUMN = "dueDate";

    private static MySqlHomeReservationDAO instance;

    public static MySqlHomeReservationDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlHomeReservationDAO();
        }
        return instance;
    }

    private MySqlHomeReservationDAO() {

    }

    public boolean isCreatedByBookAndUser(Integer bookId, User user) throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT homereservation.id FROM homereservation WHERE (homereservation.bookId = ?) AND (homereservation.userId = ?) LIMIT 1")){

            stm.setInt(1,bookId);
            stm.setInt(2,user.getId());

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }

    public HomeReservation getById(Integer reservationId) throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT homereservation.id, homereservation.bookId,homereservation.userId, homereservation.dueDate " +
                             "FROM homereservation WHERE homereservation.id = ? LIMIT 1")) {

            stm.setInt(1, reservationId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                HomeReservation homeReservation = new HomeReservation();
                homeReservation.setId(rs.getInt(ID_COLUMN));
                homeReservation.setBookId(rs.getInt(BOOK_ID_COLUMN));
                homeReservation.setUserId(rs.getInt(USER_ID_COLUMN));
                homeReservation.setDueDate(rs.getDate(DUE_DATE_COLUMN));
                return homeReservation;
            }
        }
        return null;
    }

    @Override
    public void deleteOverdue() throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "DELETE FROM homereservation WHERE homereservation.dueDate < NOW()")) {

            stm.executeUpdate();
        }
    }
}
