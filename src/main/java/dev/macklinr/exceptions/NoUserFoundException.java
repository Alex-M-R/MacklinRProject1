package dev.macklinr.exceptions;

public class NoUserFoundException extends RuntimeException
{
    public NoUserFoundException(String message)
    {
        super(message);
    }

}
