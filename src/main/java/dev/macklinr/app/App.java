package dev.macklinr.app;

import com.google.gson.Gson;
import dev.macklinr.daos.ComplaintDaoDB;
import dev.macklinr.daos.MeetingDaoDB;
import dev.macklinr.daos.UserDaoDB;
import dev.macklinr.dtos.LoginCredentials;
import dev.macklinr.entities.*;
import dev.macklinr.exceptions.IllegalRoleException;
import dev.macklinr.exceptions.NoUserFoundException;
import dev.macklinr.exceptions.PasswordMismatchException;
import dev.macklinr.services.*;
import dev.macklinr.utils.InputValidation;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import jdk.swing.interop.SwingInterOpUtils;

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

    public static final LoginService loginService = new LoginServiceImplementation(new UserDaoDB()); // shouldn't conflict with user service

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

        // Update Complaint Status Handler
        Handler patchComplaintStatus = ctx ->
        {
            int id = InputValidation.validatePositiveInt(ctx.pathParam("id"));

            if (id > 0)
            {
                Complaint existing = complaintService.getComplaintByID(id);

                if (existing == null)
                {
                    // we have a problem
                    return;
                }

                String newStatus = ctx.pathParam("status");
                newStatus = newStatus.toLowerCase();

               switch(newStatus)
               {
                   case "resolved":
                       existing = complaintService.updateComplaintStatus(id, Priority.RESOLVED);
                       break;
                   case "high":
                       existing = complaintService.updateComplaintStatus(id, Priority.HIGH);
                       break;
                   case "low":
                       existing = complaintService.updateComplaintStatus(id, Priority.LOW);
                       break;
                   case "ignored":
                       existing = complaintService.updateComplaintStatus(id, Priority.IGNORED);
                       break;
                   default:
                       ctx.status(400);
                       ctx.result("Something went wrong");
                       return;
               }
               ctx.result(ToJson(existing));

            }
            else
                ctx.status(400);
        };

        // Update complaint meeting Handler
        Handler patchComplaintMeeting = ctx ->
        {
            int id = InputValidation.validatePositiveInt(ctx.pathParam("id"));

            if (id > 0)
            {
                // valid complaint
                int meetingID = InputValidation.validatePositiveInt((ctx.pathParam("meetingID")));

                if (meetingID > 0)
                {
                    Complaint existing = complaintService.getComplaintByID(id);

                    existing.setMeetingID(meetingID);

                    Complaint updated = complaintService.updateComplaint(existing);

                    ctx.result(ToJson(updated));
                }
            }
        };



        // meeting Handlers
        Handler createMeetingHandler = ctx ->
        {
            ctx.status(201);
            ctx.result(ToJson(meetingService.registerMeeting(FromJson(ctx.body(),Meeting.class))));
        };

        Handler getMeetingsHandler = ctx -> ctx.result(ToJson(meetingService.getAllMeetings()));

        Handler getMeetingByIDHandler = ctx ->
        {
            int id = InputValidation.validatePositiveInt(ctx.pathParam("id"));
                ctx.result(ToJson(meetingService.getMeetingByID(id)));
        };




        // app_user Handlers

        Handler createUserHandler = ctx ->
        {
            User user = FromJson(ctx.body(), User.class);
            System.out.println(user.getRole());
            User registeredUser = userService.registerUser(user);

            System.out.println(registeredUser);
            ctx.status(201);
            ctx.result(ToJson(registeredUser));
        };

        Handler getAllUsersHandler = ctx ->
        {
            List<User> result = userService.getAllUsers();

            String param = ctx.queryParam("role");

            if (param != null)
            {
                param = param.toUpperCase();
                try
                {

                    Role filter = Role.valueOf(param);

                    result = result.stream().filter(user -> user.getRole() == filter).collect(Collectors.toList());
                }
                catch (IllegalArgumentException e)
                {
                    // bad param. Just ignore
                    e.printStackTrace();
                }
            }
            ctx.result(ToJson(result));
        };

        Handler patchUser = ctx ->
        {
            int id = InputValidation.validatePositiveInt(ctx.pathParam("id"));

            Role newRole = InputValidation.validateRole(ctx.pathParam("role"));

            userService.setUserRole(id, newRole);
        };




        // setup routes

        // complaint routes
        app.post("/complaints", createComplaintHandler);
        app.get("/complaints", getAllComplaintsHandler);    // also does get all complaints with specific meeting ID
        app.patch("/complaints/{id}/{status}", patchComplaintStatus);
        app.put("/complaints/{id}/{meetingID}", patchComplaintMeeting);

        // meeting routes
        app.post("/meetings", createMeetingHandler);
        app.get("/meetings", getMeetingsHandler);
        app.get("/meetings/{id}", getMeetingByIDHandler);

        // app_user routes
        app.post("/users", createUserHandler);
        app.get("/users", getAllUsersHandler);
        app.patch("/users/{id}/{role}",patchUser);





        // Login is NOT a RESTful endpoint
        app.post("/login", ctx ->
        {
            LoginCredentials credentials = FromJson(ctx.body(),LoginCredentials.class);

            User user = loginService.validateUser(credentials.getUsername(), credentials.getPassword());

            ctx.result(ToJson(user));

        });


        // when the exception PasswordMismatchException is thrown and is never caught
        // it will be passed to this function along with the ctx from that http request
       app.exception(PasswordMismatchException.class, (exception, ctx) ->
       {
           ctx.status(400);
           ctx.result("password did not match");
       });

       app.exception(NoUserFoundException.class, (exception, ctx) ->
       {
           ctx.status(404);
           ctx.result("Employee not found ");
       });

       app.exception(IllegalRoleException.class, (exception, ctx) ->
       {
          ctx.status(400);
          ctx.result(exception.getMessage());
       });

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
