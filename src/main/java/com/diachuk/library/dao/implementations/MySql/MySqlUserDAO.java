package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.dao.entities.User;
import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.IUserDAO;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 01.12.2016.
 */
public class MySqlUserDAO implements IUserDAO {

    static final String ID_COLUMN = "id";
    static final String NAME_COLUMN = "name";
    static final String SURNAME_COLUMN = "surname";
    static final String ROLE_ID_COLUMN = "roleId";
    static final String EMAIL_COLUMN = "email";
    static final String PASSWORD_COLUMN = "password";
    static final String REGISTRATION_DATE_COLUMN = "registrationDate";

    private static MySqlUserDAO instance;

    public static MySqlUserDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlUserDAO();
        }
        return instance;
    }

    private MySqlUserDAO() {

    }

    /**
     * Searches the user by id.
     *
     * @param userId
     * @return Returns user if exists.
     * @throws SQLException
     */
    public User findById(Integer userId) throws SQLException {
        User user = null;
        try (Connection conn = MySqlDAOFactory.createConnection();
             PreparedStatement stm = conn.prepareStatement(
                     "SELECT user.id, user.name, user.roleId, user.surname, user.email, user.password, user.registrationDate " +
                             "FROM user WHERE id = ? LIMIT 1")) {

            stm.setInt(1, userId);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                user = fillUserDataFromResultSet(rs);
                rs.close();
                return user;
            }
            return null;
        } catch (SQLException e) {
            //// TODO: 19.01.2017 logging
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Searches the user by email.
     *
     * @param email
     * @return Returns user if exists.
     */
    public User findByEmail(String email) throws SQLException {
        User user = null;
        try (Connection conn = MySqlDAOFactory.createConnection();
             PreparedStatement stm = conn.prepareStatement(
                     "SELECT user.id, user.name, user.roleId, user.surname, user.email, user.password, user.registrationDate " +
                             "FROM user WHERE email = ? LIMIT 1")) {

            stm.setString(1, email);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                user = fillUserDataFromResultSet(rs);
                rs.close();
                return user;
            }
            return null;

        } catch (SQLException e) {
            //// TODO: 19.01.2017 logging
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Checks whether the user with such email exists or not.
     *
     * @param email - user email to check.
     * @return true if user exists, otherwise - false.
     * @throws SQLException
     */
    public boolean checkExistenceByEmail(String email) throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("SELECT user.id FROM user WHERE user.email = ? LIMIT 1")) {

            stm.setString(1, email);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds new user to the DB.
     *
     * @param user
     * @return Returns the user that was added.
     * @throws SQLException
     */
    public boolean insertUser(User user) throws SQLException {
        if (user == null) {
            return false;
        }
        try (Connection conn = MySqlDAOFactory.createConnection();
             PreparedStatement stm =
                     conn.prepareStatement("INSERT INTO USER (name,surname,roleId,email,password,registrationDate) VALUES (?,?,?,?,?,current_date())")) {

            stm.setString(1, user.getName());
            stm.setString(2, user.getSurname());
            stm.setInt(3, user.getRoleId());
            stm.setString(4, user.getEmail());
            stm.setString(5, user.getPassword());

            if (stm.executeUpdate() > 0) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Updates basic info about user: name, surname, email, password.
     *
     * @param user user with updated info.
     * @return true if success, otherwise - false.
     * @throws SQLException
     */
    public boolean updateBasic(User user) throws SQLException {
        if (user == null) {
            return false;
        }
        try (Connection conn = MySqlDAOFactory.createConnection();
             PreparedStatement stm = conn.prepareStatement("UPDATE USER " +
                     "SET name = ?, surname = ?, email = ?, password = ? WHERE id = ? ")) {

            stm.setString(1, user.getName());
            stm.setString(2, user.getSurname());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getPassword());
            stm.setInt(5, user.getId());

            if (stm.executeUpdate() > 0) {
                return true;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean updateRole(Integer roleId, Integer userId) throws SQLException {
        if (userId == null || roleId == null) {
            return false;
        }
        try (Connection conn = MySqlDAOFactory.createConnection();
             PreparedStatement stm = conn.prepareStatement("UPDATE USER " +
                     "SET user.roleId = ? WHERE user.id = ? ")) {

            stm.setInt(1, roleId);
            stm.setInt(2, userId);

            if (stm.executeUpdate() > 0) {
                return true;
            }
            return false;

        }
    }

    /**
     * Deletes user by id.
     *
     * @param userId - id of user to delete.
     * @return true if success, otherwise - false.
     * @throws SQLException
     */
    public boolean deleteById(Integer userId) throws SQLException {
        if (userId == null) {
            return false;
        }
        try (Connection conn = MySqlDAOFactory.createConnection();
             PreparedStatement stm = conn.prepareStatement("DELETE FROM USER WHERE id = ? ")) {

            stm.setInt(1, userId);

            return stm.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    //-----------------------------------------
    private User fillUserDataFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setPassword(rs.getString("password"));
        user.setRoleId(rs.getInt("roleId"));
        user.setEmail(rs.getString("email"));
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setRegistrationDate(rs.getDate("registrationDate"));
        return user;
    }
}

