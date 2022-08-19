package dev.macklinr.utils;

public class InputValidation
{
    public static int ValidatePositiveInt(String s)
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

}
