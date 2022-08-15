package dev.macklinr.daotests;

import dev.macklinr.daos.MeetingDAO;
import dev.macklinr.daos.MeetingDaoDB;
import dev.macklinr.utils.ConnectionUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeetingTests
{
    private static final String meetingTable = "testMeeting";

    MeetingDAO meetingDAO = new MeetingDaoDB(meetingTable);


    @BeforeAll
    static void setup()
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "<pending ERD approval>";

            Statement statement = conn.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // Test cases pending ERD approval



    @AfterAll
    static void teardown()
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "drop table " + meetingTable + ";";

            Statement statement = conn.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
