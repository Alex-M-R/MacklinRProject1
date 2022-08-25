package dev.macklinr.utils;

import dev.macklinr.entities.Role;
import dev.macklinr.exceptions.IllegalRoleException;

public class InputValidation
{
    public static int validatePositiveInt(String s)
    {
        int id = -1;

        try
        {
            id = Integer.parseInt(s);

            if (id <= 0)
                id = -1;

        }
        catch (NumberFormatException e)
        {
            // maybe throw exception instead of falling through and returning id as negative on failure
        }

        return id;
    }

    public static Role validateRole(String s)
    {
        try
        {
            Role role = Role.valueOf(s.toUpperCase());
            return role;
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalRoleException(s + " is not a valid role.");
        }
    }


}
