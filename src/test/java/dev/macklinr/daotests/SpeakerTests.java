package dev.macklinr.daotests;

import dev.macklinr.daos.*;
import dev.macklinr.entities.*;
import dev.macklinr.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpeakerTests
{
    UserDAO userDB = new UserDaoDB("usertest");
    MeetingDAO meetingDB = new MeetingDaoDB("meetingtest");
    SpeakerDAO speakerDB = new SpeakerDaoDB("speakertest", "usertest", "meetingtest");

    @BeforeAll
    static void setup()
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "create table usertest\n" +
                    "(\n" +
                    "id serial primary key,\n" +
                    "fname varchar(40) not null,\n" +
                    "lname varchar(40) not null,\n" +
                    "username varchar(40) not null unique,\n" +
                    "password varchar(40) not null,\n" +
                    "role varchar(40) check (role in ('INACTIVE', 'CONSTITUENT', 'COUNCIL'))\n" +
                    ");\n" +
                    "\n" +
                    "create table meetingtest\n" +
                    "(\n" +
                    "id serial primary key,\n" +
                    "address varchar(200),\n" +
                    "time int,\n" +
                    "summary varchar(200)\n" +
                    ");\n" +
                    "\n" +
                    "\n" +
                    "create table speakertest\n" +
                    "(\n" +
                    "id serial primary key,\n" +
                    "meeting_id int references meetingtest(id),\n" +
                    "speaker_id int references usertest(id),\t\n" +
                    "status varchar(20) check (status in ('PENDING', 'APPROVED', 'DENIED')) default 'PENDING'\n" +
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
    void create_speaker_test()
    {
        // create a meeting
        Meeting aMeeting = new Meeting(0,"testing area",0,"testing speaker request");
        // create a user
        User aUser = new User(0,"A","Tester","tester1","password", Role.CONSTITUENT);

        // insert meeting/user
        aUser = userDB.createUser(aUser);

        aMeeting = meetingDB.createMeeting(aMeeting);
        // create request for user to speak at meeting
        Assertions.assertTrue(speakerDB.createSpeakerRequest(aMeeting.getId(), aUser.getId()));
    }

    @Test
    @Order(2)
    void get_all_speakers_test()
    {
        int b = userDB.createUser(new User(0, "B", "Tester", "tester2", "password", Role.CONSTITUENT)).getId();
        int c = userDB.createUser(new User(0, "C", "Tester", "tester3", "password", Role.CONSTITUENT)).getId();
        int d = userDB.createUser(new User(0, "D", "Tester", "tester4", "password", Role.CONSTITUENT)).getId();
        int e = userDB.createUser(new User(0, "E", "Tester", "tester5", "password", Role.CONSTITUENT)).getId();
        int f = userDB.createUser(new User(0, "F", "Tester", "tester6", "password", Role.CONSTITUENT)).getId();

        int meeting2 = meetingDB.createMeeting(new Meeting(0, "testing area 2", 0, "Second test meeting")).getId();

        speakerDB.createSpeakerRequest(meeting2, b);
        speakerDB.createSpeakerRequest(meeting2, c);
        speakerDB.createSpeakerRequest(meeting2, d);
        speakerDB.createSpeakerRequest(meeting2, e);
        speakerDB.createSpeakerRequest(meeting2, f);

        List<Speaker> speakerList = speakerDB.getAllSpeakers();

        Assertions.assertEquals(6, speakerList.size());

    }

    @Test
    @Order(3)
    void get_all_speakers_for_meeting_test()
    {
        List<Speaker> meeting2SpeakerList = speakerDB.getAllSpeakersByMeetingId(2);

        Assertions.assertEquals(5, meeting2SpeakerList.size());
    }

    @Test
    @Order(4)
    void update_speaker_request_state()
    {
        speakerDB.updateSpeakerState(1, RequestState.APPROVED);
        // didn't make get speaker by ID method. so just filter the list for the correct id.
        List<Speaker> speakerList = speakerDB.getAllSpeakers().stream().filter(speaker -> speaker.getId() == 1).collect(Collectors.toList());
        Assertions.assertEquals(RequestState.APPROVED, speakerList.get(0).getState());
    }




    @AfterAll
    static void teardown()
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "drop table speakertest;\n" +
                    "drop table usertest;\n" +
                    "drop table meetingtest;";

            Statement statement = conn.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
