package com.diachuk.library.dao.factory.MySql;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by VA-N_ on 18.12.2016.
 */
public class MySqlConnectionPool {

    private static DataSource dataSource;

    static{
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/library");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
