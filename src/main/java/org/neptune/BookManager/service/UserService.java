package org.neptune.BookManager.service;

import org.neptune.BookManager.model.User;
import org.neptune.BookManager.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public int addUser(User user) {
        return userDAO.addUser(user);
    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public User getUser(String email) {
        return userDAO.selectByEmail(email);
    }

    public void updatePassword(User user) {
        userDAO.updatePassword(user);
    }

}
