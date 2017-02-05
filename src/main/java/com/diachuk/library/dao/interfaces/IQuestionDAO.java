package com.diachuk.library.dao.interfaces;

import java.sql.SQLException;

/**
 * Created by VA-N_ on 29.12.2016.
 */
public interface IQuestionDAO {
    void deleteOlderThan(Integer questionLifeTime) throws SQLException;
}
