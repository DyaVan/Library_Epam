package com.diachuk.library.dao.implementations.MySql;

import com.diachuk.library.dao.interfaces.IVoteDAO;

/**
 * Created by VA-N_ on 20.01.2017.
 */
public class MySqlVoteDAO implements IVoteDAO {
    private static MySqlVoteDAO instance;

    public static MySqlVoteDAO getInstance() {
        if (instance == null) {
            return instance = new MySqlVoteDAO();
        }
        return instance;
    }

    private MySqlVoteDAO(){

    }
}
