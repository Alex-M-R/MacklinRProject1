package dev.macklinr.app;

import dev.macklinr.daos.ComplaintDaoDB;
import dev.macklinr.daos.MeetingDaoDB;
import dev.macklinr.daos.UserDaoDB;
import dev.macklinr.services.*;
import io.javalin.Javalin;

public class App
{
    private static final String userTable = "sysuser";
    private static final String complaintTable = "complaint";
    private static final String meetingTable = "meeting";

    public static final UserService userService = new UserServiceImplementation(new UserDaoDB(userTable));
    public static final ComplaintService complaintService = new ComplaintServiceImplementation(new ComplaintDaoDB(complaintTable));
    public static final MeetingService meetingService = new MeetingServiceImplementation(new MeetingDaoDB(meetingTable));


    public static void main(String[] args)
    {

        Javalin app = Javalin.create(config ->
        {
           config.enableDevLogging();
           config.enableCorsForAllOrigins();
        });

        // setup handlers

        // setup routes


        app.start();
    }
}
