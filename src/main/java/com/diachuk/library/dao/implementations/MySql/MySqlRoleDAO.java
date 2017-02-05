package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.IRequestDAO;
import com.diachuk.library.dao.interfaces.IRoleDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 29.12.2016.
 */
public class MySqlRoleDAO implements IRoleDAO {

    private static MySqlRoleDAO instance;

    public static MySqlRoleDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlRoleDAO();
        }
        return instance;
    }

    private MySqlRoleDAO(){

    }

    /**
     * Retrieves the access level by roleId.
     * @param roleId
     * @return int - access level value.
     * @throws SQLException
     */
    public int getAccessLevel(int roleId) throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("SELECT role.accessLevel FROM role WHERE role.id = ? LIMIT 1")) {

            stm.setInt(1,roleId);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("accessLevel");
            }else {
                //todo:Logging
                return 0;
            }
        }
    }

    public boolean deleteRole(int roleId) throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("DELETE FROM role WHERE role.id = ?")) {

            stm.setInt(1,roleId);

            if (stm.executeUpdate() > 0) {
                return true;
            }
            return false;
        }
    }

    public boolean updateRole(Integer roleId, String roleName, Integer roleAccessLevel) throws SQLException {
        try (Connection connection = MySqlDAOFactory.createConnection();
             PreparedStatement stm = connection.prepareStatement("UPDATE role SET role.name = ?, role.accessLevel = ? " +
                     "WHERE role.id = ?")) {
            stm.setString(1,roleName);
            stm.setInt(2,roleAccessLevel);
            stm.setInt(3,roleId);

            if (stm.executeUpdate() > 0) {
                return true;
            }
            return false;
        }
    }
}
