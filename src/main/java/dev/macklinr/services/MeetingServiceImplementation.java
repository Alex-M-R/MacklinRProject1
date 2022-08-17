package dev.macklinr.services;

import dev.macklinr.daos.MeetingDAO;
import dev.macklinr.entities.Meeting;

import java.util.List;

public class MeetingServiceImplementation implements MeetingService
{
    private final MeetingDAO meetingDAO;

    public MeetingServiceImplementation(MeetingDAO meetingDAO) {this.meetingDAO = meetingDAO;}

    @Override
    public Meeting registerMeeting(Meeting meeting)
    {
        return this.meetingDAO.createMeeting(meeting);
    }

    @Override
    public List<Meeting> getAllMeetings() {return this.meetingDAO.getAllMeetings();}
}
