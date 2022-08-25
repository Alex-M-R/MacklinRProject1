package dev.macklinr.services;

import dev.macklinr.entities.Role;
import dev.macklinr.entities.User;

import java.util.List;

public interface UserService
{
    User registerUser(User user);

    List<User> getAllUsers();

    boolean setUserRole(int id, Role newRole);

}
