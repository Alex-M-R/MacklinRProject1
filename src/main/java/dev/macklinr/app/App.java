package dev.macklinr.app;

import com.google.gson.Gson;
import dev.macklinr.daos.ComplaintDaoDB;
import dev.macklinr.daos.MeetingDaoDB;
import dev.macklinr.daos.UserDaoDB;
import dev.macklinr.entities.Complaint;
import dev.macklinr.entities.Meeting;
import dev.macklinr.entities.User;
import dev.macklinr.services.*;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;
import java.util.stream.Collectors;

public class App
{
    private static final String userTable = "app_user";
    private static final String complaintTable = "complaint";
    private static final String meetingTable = "meeting";

    public static final UserService userService = new UserServiceImplementation(new UserDaoDB(userTable));
    public static final ComplaintService complaintService = new ComplaintServiceImplementation(new ComplaintDaoDB(complaintTable));
    public static final MeetingService meetingService = new MeetingServiceImplementation(new MeetingDaoDB(meetingTable));

    /*
    ctx.status(int);
    relevant codes to remember, and probably use this time around:
    - 200 'OK'              - sent by default with response assuming no errors or manual change.
    - 201 'Created'         - only use when creating new entity
    - 400 'Bad Request'     - bad input
    - 401 'Unauthorized'    - trying to do something without having correct user access (but tbh shouldn't be able to on front end anyway?)
    - 404 'Not Found'       - Duh
     */

    public static void main(String[] args)
    {

        Javalin app = Javalin.create(config ->
        {
            config.enableDevLogging();
            config.enableCorsForAllOrigins();
        });

        // setup handlers

        // Complaint Handlers
        Handler createComplaintHandler = ctx ->
        {
            ctx.status(201);
            ctx.result(ToJson((complaintService.registerComplaint(FromJson(ctx.body(),Complaint.class)))));
        };

        Handler getAllComplaintsHandler = ctx ->    // also can get all complaints with specific meeting id if given a valid query param
        {
            List<Complaint> result = complaintService.getAllComplaints();

            // Trying out just filtering in handler instead. Wouldn't need a separate service/dao method this way.
            // + handler is already checking if the id is valid... so just use it if it is.
            String param = ctx.queryParam("meetingID");

            if (param != null)
            {
                try
                {   // if optional query param is a valid int. filter complaints based on param
                    int meetingID = Integer.parseInt(param);

                    // filter complaints on meetingID
                    result = result.stream().filter(complaint -> complaint.getMeetingID() == meetingID).collect(Collectors.toList());

                    // keeping until test(s) shows above works fine
                    //   result = complaintService.getAllComplaintsForMeeting(meetingID);
                }
                catch (IllegalArgumentException e)
                {
                    // bad param. Just ignore
                    e.printStackTrace();
                }
            }
            ctx.result(ToJson(result));
        };


        // meeting Handlers
        Handler createMeetingHandler = ctx -> ctx.result(ToJson(meetingService.registerMeeting(FromJson(ctx.body(),Meeting.class))));

        Handler getMeetingsHandler = ctx -> ctx.result(ToJson(meetingService.getAllMeetings()));





        // app_user Handlers

        Handler createUserHandler = ctx ->
        {
            User user = FromJson(ctx.body(), User.class);
            User registeredUser = userService.registerUser(user);

            ctx.status(201);
            ctx.result(ToJson(registeredUser));
        };
        // setup routes

        // complaint routes
        app.post("/complaints", createComplaintHandler);
        app.get("/complaints", getAllComplaintsHandler);

        // meeting routes
        app.post("/meetings", createMeetingHandler);
        app.get("/meetings", getMeetingsHandler);

        // app_user routes

        app.start();
    }

    static <T> T FromJson(String json, Class<T> type)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    static String ToJson(Object obj)
    {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
