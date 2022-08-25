package dev.macklinr.daos;

import dev.macklinr.entities.Role;
import dev.macklinr.entities.User;

import java.util.List;

public interface UserDAO
{
    User createUser(User user);

    User getUserByUserName(String username);

    List<User> getAllUsers();

    boolean setRole(int id, Role newRole);
}
