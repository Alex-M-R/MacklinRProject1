package dev.macklinr.daos;

import dev.macklinr.entities.Meeting;

import java.util.List;

public interface MeetingDAO
{
    Meeting createMeeting(Meeting meeting);

    Meeting getMeetingByID(int id);
    List<Meeting> getAllMeetings();
}
