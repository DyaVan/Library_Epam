package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.dao.interfaces.IFutureBookDAO;

/**
 * Created by VA-N_ on 29.12.2016.
 */
public class MySqlFutureBookDAO implements IFutureBookDAO {

    private static MySqlFutureBookDAO instance;

    public static MySqlFutureBookDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlFutureBookDAO();
        }
        return instance;
    }

    private MySqlFutureBookDAO(){

    }

}
