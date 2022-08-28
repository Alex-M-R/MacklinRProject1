package dev.macklinr.utils;

import dev.macklinr.entities.RequestState;
import dev.macklinr.entities.Role;
import dev.macklinr.exceptions.IllegalRequestStateException;
import dev.macklinr.exceptions.IllegalRoleException;

public class InputValidation
{
    private InputValidation(){}

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
            return Role.valueOf(s.toUpperCase());
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalRoleException(s + " is not a valid role.");
        }
    }

    public static RequestState validateState(String s)
    {
        try
        {
            return RequestState.valueOf(s.toUpperCase());
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalRequestStateException(s + " is not a valid role.");
        }
    }



}
