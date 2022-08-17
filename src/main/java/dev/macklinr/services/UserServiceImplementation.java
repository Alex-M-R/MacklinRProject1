package dev.macklinr.services;

import dev.macklinr.daos.UserDAO;
import dev.macklinr.daos.UserDaoDB;
import dev.macklinr.entities.User;

public class UserServiceImplementation implements UserService
{
    private final UserDAO userDAO;

    public UserServiceImplementation(UserDAO userDAO) {this.userDAO = userDAO;}

    @Override
    public User registerUser(User user)
    {
        return this.userDAO.createUser(user);
    }
}
