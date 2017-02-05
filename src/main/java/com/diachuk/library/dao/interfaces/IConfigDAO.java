package com.diachuk.library.dao.interfaces;

import com.diachuk.library.dao.entities.Config;

import java.sql.SQLException;

/**
 * Created by VA-N_ on 22.01.2017.
 */
public interface IConfigDAO {

    Config getQuestionLifetime() throws SQLException;

    Config getNewsLifetime() throws SQLException;

    Config getMaxHomeReservationDuration() throws SQLException;

    Config getMaxRRoomReservationDuration() throws SQLException;
}
