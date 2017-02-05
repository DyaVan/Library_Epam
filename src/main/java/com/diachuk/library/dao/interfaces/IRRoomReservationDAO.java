package com.diachuk.library.dao.interfaces;

import java.sql.SQLException;

/**
 * Created by VA-N_ on 19.01.2017.
 */
public interface IRRoomReservationDAO {
    void deleteOverdue() throws SQLException;
}
