package dev.macklinr.services;

import dev.macklinr.entities.Meeting;
import dev.macklinr.entities.User;

import java.util.List;

public interface MeetingService
{
    Meeting registerMeeting(Meeting meeting);

    Meeting getMeetingByID(int id);
    List<Meeting> getAllMeetings();
}
