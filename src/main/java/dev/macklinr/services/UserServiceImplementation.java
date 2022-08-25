package dev.macklinr.services;

import dev.macklinr.daos.UserDAO;
import dev.macklinr.daos.UserDaoDB;
import dev.macklinr.entities.Role;
import dev.macklinr.entities.User;

import java.util.List;

public class UserServiceImplementation implements UserService
{
    private final UserDAO userDAO;

    public UserServiceImplementation(UserDAO userDAO) {this.userDAO = userDAO;}

    @Override
    public User registerUser(User user)
    {
        return this.userDAO.createUser(user);
    }

    @Override
    public List<User> getAllUsers()
    {
        return this.userDAO.getAllUsers();
    }

    @Override
    public boolean setUserRole(int id, Role newRole) {
        return this.userDAO.setRole(id, newRole);
    }
}
