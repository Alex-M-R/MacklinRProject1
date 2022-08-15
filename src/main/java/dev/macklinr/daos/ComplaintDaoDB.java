package dev.macklinr.daos;

public class ComplaintDaoDB implements ComplaintDAO
{
    String tableName;

    public ComplaintDaoDB()
    {
        super();
        tableName = "complaint";  // default name
    }

    public ComplaintDaoDB(String tableName) {this.tableName = tableName;}
}
