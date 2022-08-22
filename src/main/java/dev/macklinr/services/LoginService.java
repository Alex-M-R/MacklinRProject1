package dev.macklinr.services;

import dev.macklinr.entities.User;

public interface LoginService
{
    User validateUser(String username, String password);
}
