package com.diachuk.library.dao.factory.MySql;

import com.diachuk.library.dao.factory.DAOFactory;
import com.diachuk.library.dao.implementations.MySql.*;
import com.diachuk.library.dao.interfaces.*;

import java.sql.Connection;

// todo method create should return an instance and so on
/**
 * Created by VA-N_ on 01.12.2016.
 */
public class MySqlDAOFactory extends DAOFactory {

    private static MySqlDAOFactory instance;

    public static MySqlDAOFactory getInstance() {
        if (instance == null) {
            return instance = new MySqlDAOFactory();
        }
        return instance;
    }

    private MySqlDAOFactory(){

    }

    public static Connection createConnection() {
        return MySqlConnectionPool.getConnection();
    }

    @Override
    public IBannedBookDAO getBannedBookDAO() {
        return MySqlBannedBookDAO.getInstance();
    }

    @Override
    public IBookDAO getBookDAO() {
        return MySqlBookDAO.getInstance();
    }

    @Override
    public IUserDAO getUserDAO() {
        return MySqlUserDAO.getInstance();
    }

    @Override
    public IRoleDAO getRoleDAO() {
        return MySqlRoleDAO.getInstance();
    }

    @Override
    public INewsPostDAO getNewsPostDAO() {
        return MySqlNewsPostDAO.getInstance();
    }

    @Override
    public IQuestionDAO getQuestionDAO() {
        return MySqlQuestionDAO.getInstance();
    }

    @Override
    public IVoteDAO getVoteDAO() {
        return MySqlVoteDAO.getInstance();
    }

    @Override
    public ICrossTableDAO getCrossTableDAO() {
        return MySqlCrossTableDAO.getInstance();
    }

    @Override
    public IConfigDAO getConfigDAO() {
        return MySqlConfigDAO.getInstance();
    }

    @Override
    public IHomeReservationDAO getHomeReservationDAO() {
        return MySqlHomeReservationDAO.getInstance();
    }

    @Override
    public IRRoomReservationDAO getIRRoomReservationDAO() {
        return MySqlRRoomReservationDAO.getInstance();
    }

    @Override
    public IRequestDAO getRequestDAO() {
        return MySqlRequestDAO.getInstance();
    }

    @Override
    public IFutureBookDAO getFutureBookDAO() {
        return MySqlFutureBookDAO.getInstance();
    }

    @Override
    public IBookLoanDAO getBookLoanDAO() {
        return MySqlBookLoanDAO.getInstance();
    }
}
