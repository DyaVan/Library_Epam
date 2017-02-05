package com.diachuk.library.dao.interfaces;

import java.sql.SQLException;

/**
 * Created by VA-N_ on 18.12.2016.
 */
public interface IHomeReservationDAO {
    void deleteOverdue() throws SQLException;
}
