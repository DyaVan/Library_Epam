package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.commands.ICommand;
import com.diachuk.library.dao.entities.Config;
import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.IConfigDAO;

import java.sql.*;

/**
 * Created by VA-N_ on 22.01.2017.
 */
public class MySqlConfigDAO implements IConfigDAO {

     static final String ID_COLUMN = "id";
     static final String PARAMETER_NAME_COLUMN = "parameterName";
     static final String VALUE_COLUMN = "value";

    private static MySqlConfigDAO instance;

    public static MySqlConfigDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlConfigDAO();
        }
        return instance;
    }

    private MySqlConfigDAO() {

    }

    public Config getMaxDurationOfBookLoan() throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT id, parameterName, value FROM config WHERE parameterName = \"Maximal Duration Of BookLoan\"")) {

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Config config = fillConfigFromResultSet(rs);
                return config;
            }
            return null;
        }
    }

    public Config getMaxBooksToGive() throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT id, parameterName, value FROM config WHERE parameterName = \"Maximum Books To Give\"")) {

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Config config = fillConfigFromResultSet(rs);
                return config;
            }
            return null;
        }
    }

    @Override
    public Config getQuestionLifetime() throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT id, parameterName, value FROM config WHERE parameterName = \"Question Lifetime\"")) {

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Config config = fillConfigFromResultSet(rs);
                return config;
            }
            return null;
        }
    }

    @Override
    public Config getNewsLifetime() throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT id, parameterName, value FROM config WHERE parameterName = \"News Lifetime\"")) {

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Config config = fillConfigFromResultSet(rs);
                return config;
            }
            return null;
        }
    }

    @Override
    public Config getMaxHomeReservationDuration() throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT id, parameterName, value FROM config WHERE parameterName = \"Maximal Home Reservation Duration\"")) {

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Config config = fillConfigFromResultSet(rs);
                return config;
            }
            return null;
        }
    }

    @Override
    public Config getMaxRRoomReservationDuration() throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement(
                     "SELECT id, parameterName, value FROM config WHERE parameterName = \"Maximal RRoom Reservation Duration\"")) {

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Config config = fillConfigFromResultSet(rs);
                return config;
            }
            return null;
        }
    }

    private Config fillConfigFromResultSet(ResultSet rs) throws SQLException {
        Config config = new Config();
        config.setId(rs.getInt(ID_COLUMN));
        config.setParameterName(rs.getString(PARAMETER_NAME_COLUMN));
        config.setValue(rs.getInt(VALUE_COLUMN));
        return config;
    }
}
