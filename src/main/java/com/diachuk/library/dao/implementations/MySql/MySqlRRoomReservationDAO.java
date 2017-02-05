package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.dao.entities.Book;
import com.diachuk.library.dao.entities.RRoomReservation;
import com.diachuk.library.dao.entities.User;
import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.IRRoomReservationDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by VA-N_ on 19.01.2017.
 */
public class MySqlRRoomReservationDAO implements IRRoomReservationDAO {

     final static String ID_COLUMN = "id";
     final static String BOOK_ID_COLUMN = "bookId";
     final static String USER_ID_COLUMN = "userId";
     final static String DUE_TIME_COLUMN = "dueTime";

    private static MySqlRRoomReservationDAO instance;

    public static MySqlRRoomReservationDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlRRoomReservationDAO();
        }
        return instance;
    }

    private MySqlRRoomReservationDAO(){

    }

    public RRoomReservation getById(Integer reservationId) throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT rroomreservation.id, rroomreservation.bookId,rroomreservation.userId, rroomreservation.dueTime " +
                             "FROM rroomreservation WHERE rroomreservation.id = ? LIMIT 1")) {

            stm.setInt(1, reservationId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                RRoomReservation rRoomReservation = new RRoomReservation();
                rRoomReservation.setId(rs.getInt(ID_COLUMN));
                rRoomReservation.setBookId(rs.getInt(BOOK_ID_COLUMN));
                rRoomReservation.setUserId(rs.getInt(USER_ID_COLUMN));
                rRoomReservation.setDueTime(rs.getDate(DUE_TIME_COLUMN));
                return rRoomReservation;
            }
        }
        return null;
    }

    @Override
    public void deleteOverdue() throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "DELETE FROM rroomreservation WHERE rroomreservation.dueTime < NOW()")) {

            stm.executeUpdate();
        }
    }

    public boolean isCreatedByBookAndUser(Integer bookId, User user) throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT rroomreservation.id FROM rroomreservation WHERE (rroomreservation.bookId = ?) AND (rroomreservation.userId = ?) LIMIT 1")){

            stm.setInt(1,bookId);
            stm.setInt(2,user.getId());

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }

}
