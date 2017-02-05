package com.diachuk.library.dao.interfaces;

import java.sql.SQLException;

/**
 * Created by VA-N_ on 29.12.2016.
 */
public interface INewsPostDAO {
    void deleteOlderThan(Integer newsLifeTime) throws SQLException;
}
