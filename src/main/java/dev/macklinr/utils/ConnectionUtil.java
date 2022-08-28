package dev.macklinr.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil
{
    private ConnectionUtil(){}

    public static Connection createConnection()
    {
        try
        {
            return DriverManager.getConnection(System.getenv("AZURE_PROJECT_1_DB"));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}