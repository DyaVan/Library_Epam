package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.IBannedBookDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 19.01.2017.
 */
public class MySqlBannedBookDAO implements IBannedBookDAO {

     static final String BOOK_ID_COLUMN = "bookId";

    private static MySqlBannedBookDAO instance;

    public static MySqlBannedBookDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlBannedBookDAO();
        }
        return instance;
    }

    private MySqlBannedBookDAO(){

    }

    public boolean checkExistence(Integer bookId) throws SQLException {
        if (bookId == null) {
            return false;
        }
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("SELECT bookId FROM bannedbook WHERE bookId = ? LIMIT 1")) {

            stm.setInt(1, bookId);
            ResultSet rs =  stm.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }
}
