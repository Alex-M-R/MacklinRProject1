package dev.macklinr.exceptions;

public class IllegalRequestStateException extends RuntimeException
{
    public IllegalRequestStateException(String message) {
        super(message);
    }
}
