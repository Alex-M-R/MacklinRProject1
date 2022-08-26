package dev.macklinr.services;

import dev.macklinr.daos.MeetingDAO;
import dev.macklinr.entities.Meeting;
import dev.macklinr.entities.User;

import java.util.List;
import java.util.stream.Collectors;

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
    public Meeting getMeetingByID(int id)
    {
        return this.meetingDAO.getMeetingByID(id);
    }

    // Ignore any dummy meetings in list
    @Override
    public List<Meeting> getAllMeetings() {return this.meetingDAO.getAllMeetings();}
}
