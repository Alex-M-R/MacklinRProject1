package dev.macklinr.services;

import dev.macklinr.entities.Meeting;

import java.util.List;

public interface MeetingService
{
    Meeting registerMeeting(Meeting meeting);

    List<Meeting> getAllMeetings();
}
