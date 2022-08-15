package dev.macklinr.services;

import dev.macklinr.daos.UserDAO;
import dev.macklinr.daos.UserDaoDB;

public class UserServiceImplementation implements UserService
{
    private final UserDAO userDAO;

    public UserServiceImplementation(UserDAO userDAO) {this.userDAO = userDAO;}

}
