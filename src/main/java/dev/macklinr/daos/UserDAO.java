package dev.macklinr.daos;

import dev.macklinr.entities.User;

public interface UserDAO
{
    User createUser(User user);

    User getUserByUserName(String username);
}
