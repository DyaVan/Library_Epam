package com.diachuk.library.dao.factory;

import com.diachuk.library.dao.factory.MySql.MySqlDAOFactory;
import com.diachuk.library.dao.interfaces.*;

public abstract class DAOFactory {

    public abstract IBannedBookDAO getBannedBookDAO();
    public abstract IBookDAO getBookDAO();
    public abstract IBookLoanDAO getBookLoanDAO();
    public abstract IHomeReservationDAO getHomeReservationDAO();
    public abstract IRRoomReservationDAO getIRRoomReservationDAO();
    public abstract IRequestDAO getRequestDAO();
    public abstract IFutureBookDAO getFutureBookDAO();
    public abstract IUserDAO getUserDAO();
    public abstract IRoleDAO getRoleDAO();
    public abstract INewsPostDAO getNewsPostDAO();
    public abstract IQuestionDAO getQuestionDAO();
    public abstract IVoteDAO getVoteDAO();
    public abstract ICrossTableDAO getCrossTableDAO();
    public abstract IConfigDAO getConfigDAO();

    public static DAOFactory getDAOFactory(DB dataBaseType) {
        switch (dataBaseType) {
            case MySql:{
                return MySqlDAOFactory.getInstance();
            }
            default: return null;
        }
    }

}
