package com.diachuk.library.dao.interfaces;

import com.diachuk.library.dao.entities.User;

import java.sql.SQLException;

/**
 * Created by VA-N_ on 01.12.2016.
 */
public interface IUserDAO {

    boolean checkExistenceByEmail(String email);

    boolean insertUser(User user);

    User findByEmail(String email);

    boolean updateRole(Integer roleId, Integer userId);

    boolean updateBasic(User currentUser);

}
