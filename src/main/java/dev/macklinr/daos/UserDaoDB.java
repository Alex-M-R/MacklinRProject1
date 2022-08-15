package dev.macklinr.daos;

public class UserDaoDB implements UserDAO
{
    String tableName;

    public UserDaoDB()
    {
        super();
        tableName = "sysuser";  // default name
    }

    public UserDaoDB(String tableName) {this.tableName = tableName;}
}
