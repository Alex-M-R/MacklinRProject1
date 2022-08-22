package dev.macklinr.services;

import dev.macklinr.daos.UserDAO;
import dev.macklinr.entities.User;
import dev.macklinr.exceptions.NoUserFoundException;
import dev.macklinr.exceptions.PasswordMismatchException;

public class LoginServiceImplementation implements LoginService
{

    private UserDAO userDAO;

    public LoginServiceImplementation(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    @Override
    public User validateUser(String username, String password)
    {
        User user = this.userDAO.getUserByUserName(username);

        if (user == null)
        {
            throw new NoUserFoundException("no employee found with that username");
        }

        if (!user.getPassword().equals(password))
        {
            throw new PasswordMismatchException("password does not match");
        }

        user.setPassword("");   // don't send the password back.
        return user;
    }
}
