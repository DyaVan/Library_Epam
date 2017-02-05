package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.INewsPostDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 29.12.2016.
 */
public class MySqlNewsPostDAO implements INewsPostDAO {

    private static MySqlNewsPostDAO instance;

    public static MySqlNewsPostDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlNewsPostDAO();
        }
        return instance;
    }

    private MySqlNewsPostDAO(){

    }


    @Override
    public void deleteOlderThan(Integer newsLifeTime) throws SQLException {
        if (newsLifeTime == null) {
            return;
        }

        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "DELETE FROM newspost WHERE newspost.date < NOW() - INTERVAL ? YEAR")) {
            stm.setInt(1,newsLifeTime);
            stm.executeUpdate();
        }
    }
}
