package dev.macklinr.daotests;

import dev.macklinr.daos.ComplaintDAO;
import dev.macklinr.daos.ComplaintDaoDB;
import dev.macklinr.daos.MeetingDAO;
import dev.macklinr.daos.MeetingDaoDB;
import dev.macklinr.entities.Meeting;
import dev.macklinr.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
            String sql = "create table " + meetingTable + "\n" +
                    "(\n" +
                    "id serial primary key,\n" +
                    "address varchar(200),\n" +
                    "time int,\n" +
                    "summary varchar(200)\n" +
                    ");";

            Statement statement = conn.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void create_meeting_test()
    {
        Meeting newMeeting = new Meeting(0, "an address", 0, "we meet and stuff");

        Meeting storedMeeting = meetingDAO.createMeeting(newMeeting);

        Assertions.assertEquals(1, storedMeeting.getId());
    }

    @Test
    @Order(2)
    void get_all_meetings_test()
    {
        List<Meeting> meetings = meetingDAO.getAllMeetings();

        Assertions.assertEquals(1, meetings.size());
    }

    @Test
    @Order(3)
    void get_meeting_by_id()
    {
        Meeting existing = meetingDAO.getMeetingByID(1);

        Assertions.assertEquals("we meet and stuff", existing.getSummary());
    }


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
