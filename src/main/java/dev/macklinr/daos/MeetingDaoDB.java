package dev.macklinr.daos;

public class MeetingDaoDB implements MeetingDAO
{
    String tableName;

    public MeetingDaoDB()
    {
        super();
        tableName = "meeting";  // default name
    }

    public MeetingDaoDB(String tableName) {this.tableName = tableName;}
}
