package dev.macklinr.exceptions;

public class PasswordMismatchException extends RuntimeException
{
    public PasswordMismatchException(String message)
    {
        super(message);
    }
}
