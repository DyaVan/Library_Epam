package com.diachuk.library.services;

import com.diachuk.library.dao.entities.User;
import com.diachuk.library.dao.implementations.MySql.MySqlUserDAO;
import com.diachuk.library.dao.interfaces.IUserDAO;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fake {

    UserService userService = new UserService(new FakeUserDAO());

    class FakeUserDAO implements IUserDAO {

        Map<Integer, User> storage = new HashMap<>();
        {
            User user1 = new User();
            user1.setId(1);
            user1.setName("Ivan");
            user1.setSurname("Diachuk");
            user1.setPassword("qwerty123");
            storage.put(,)
        }

        @Override

        public boolean checkExistenceByEmail(String email) {
            return false;
        }

        @Override
        public boolean insertUser(User user) {
            return true;
        }

        @Override
        public User findByEmail(String email) {
            return
        }

        @Override
        public boolean updateRole(Integer roleId, Integer userId) {
            return true;
        }

        @Override
        public boolean updateBasic(User currentUser) {
            return true;
        }
    }


    @Test
    void testValidatePassword(){

    }



}
