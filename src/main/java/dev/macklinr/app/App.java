package dev.macklinr.app;

import com.google.gson.Gson;
import dev.macklinr.daos.ComplaintDaoDB;
import dev.macklinr.daos.MeetingDaoDB;
import dev.macklinr.daos.SpeakerDaoDB;
import dev.macklinr.daos.UserDaoDB;
import dev.macklinr.dtos.LoginCredentials;
import dev.macklinr.entities.*;
import dev.macklinr.exceptions.IllegalRequestStateException;
import dev.macklinr.exceptions.IllegalRoleException;
import dev.macklinr.exceptions.NoUserFoundException;
import dev.macklinr.exceptions.PasswordMismatchException;
import dev.macklinr.services.*;
import dev.macklinr.utils.InputValidation;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;
import java.util.stream.Collectors;

public class App
{
    private static final String USER_TABLE = "app_user";
    private static final String COMPLAINT_TABLE = "complaint";
    private static final String MEETING_TABLE = "meeting";

    private static final String SPEAKER_TABLE = "speaker";

    public static final UserService userService = new UserServiceImplementation(new UserDaoDB(USER_TABLE));
    public static final ComplaintService complaintService = new ComplaintServiceImplementation(new ComplaintDaoDB(COMPLAINT_TABLE));
    public static final MeetingService meetingService = new MeetingServiceImplementation(new MeetingDaoDB(MEETING_TABLE));
    public static final SpeakerService speakerService = new SpeakerServiceImplementation(new SpeakerDaoDB(SPEAKER_TABLE, USER_TABLE, MEETING_TABLE));
    public static final LoginService loginService = new LoginServiceImplementation(new UserDaoDB()); // shouldn't conflict with user service

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
            ctx.result(toJson((complaintService.registerComplaint(fromJson(ctx.body(),Complaint.class)))));
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

                }
                catch (IllegalArgumentException e)
                {
                    // bad param. Just ignore
                    e.printStackTrace();
                }
            }
            ctx.result(toJson(result));
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
               ctx.result(toJson(existing));

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

                    ctx.result(toJson(updated));
                }
            }
        };



        // meeting Handlers
        Handler createMeetingHandler = ctx ->
        {
            ctx.status(201);
            ctx.result(toJson(meetingService.registerMeeting(fromJson(ctx.body(),Meeting.class))));
        };

        Handler getMeetingsHandler = ctx -> ctx.result(toJson(meetingService.getAllMeetings()));

        Handler getMeetingByIDHandler = ctx ->
        {
            int id = InputValidation.validatePositiveInt(ctx.pathParam("id"));
                ctx.result(toJson(meetingService.getMeetingByID(id)));
        };




        // app_user Handlers

        Handler createUserHandler = ctx ->
        {
            User user = fromJson(ctx.body(), User.class);
            User registeredUser = userService.registerUser(user);


            ctx.status(201);
            ctx.result(toJson(registeredUser));
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
            ctx.result(toJson(result));
        };

        Handler patchUser = ctx ->
        {
            int id = InputValidation.validatePositiveInt(ctx.pathParam("id"));

            Role newRole = InputValidation.validateRole(ctx.pathParam("role"));

            userService.setUserRole(id, newRole);
        };

        // Speaker handlers
        Handler createSpeakerHandler = ctx ->
        {
          int meetingID = InputValidation.validatePositiveInt(ctx.pathParam("meetingID"));
          int userID = InputValidation.validatePositiveInt(ctx.pathParam("userID"));

          if (speakerService.createSpeakerRelationship(meetingID,userID))
              ctx.status(201);
        };

        Handler getALlSpeakersHandler = ctx ->
        {
            List<Speaker> speakers = speakerService.getAllSpeakers();

            String param = ctx.queryParam("status");

            if (param != null) {
                param = param.toUpperCase();
                try {
                    RequestState filter = RequestState.valueOf(param);

                    speakers = speakers.stream().filter(speaker -> speaker.getState() == filter).collect(Collectors.toList());
                } catch (IllegalArgumentException e) {
                    // bad param. Just ignore
                    e.printStackTrace();
                }
            }
            ctx.result(toJson(speakers));
        };

        Handler getMeetingSpeakersHandler = ctx ->
        {
            // add query param to filter on the request status
            int id = InputValidation.validatePositiveInt(ctx.pathParam("meetingID"));

            // call meeting service to do a join and return users
            List<Speaker> speakers = speakerService.getSpeakersForMeeting(id);

            String param = ctx.queryParam("status");

            if (param != null)
            {
                param = param.toUpperCase();
                try
                {
                    RequestState filter = RequestState.valueOf(param);

                    speakers = speakers.stream().filter(speaker -> speaker.getState() == filter).collect(Collectors.toList());
                }
                catch (IllegalArgumentException e)
                {
                    // bad param. Just ignore
                    e.printStackTrace();
                }
            }
            ctx.result(toJson(speakers));
        };

        Handler updateSpeakerStatusHandler = ctx ->
        {
            int id = InputValidation.validatePositiveInt(ctx.pathParam("id"));

            RequestState newState = InputValidation.validateState(ctx.pathParam("state"));

            speakerService.updateSpeakerState(id, newState);
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

        //speaker routes
        app.post("/speakers/{meetingID}/{userID}", createSpeakerHandler);
        app.get("/speakers/{meetingID}", getMeetingSpeakersHandler);
        app.get("/speakers", getALlSpeakersHandler);
        app.patch("/speakers/{id}/{state}", updateSpeakerStatusHandler);
        // need a patch speaker request



        // Login is NOT a RESTful endpoint
        app.post("/login", ctx ->
        {
            LoginCredentials credentials = fromJson(ctx.body(),LoginCredentials.class);

            User user = loginService.validateUser(credentials.getUsername(), credentials.getPassword());

            ctx.result(toJson(user));

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

       app.exception(IllegalRequestStateException.class, (exception, ctx) ->
       {
          ctx.status(400);
          ctx.result(exception.getMessage());
       });

        app.start();
    }

    static <T> T fromJson(String json, Class<T> type)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    static String toJson(Object obj)
    {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
