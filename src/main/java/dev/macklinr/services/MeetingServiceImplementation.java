package dev.macklinr.services;

import dev.macklinr.daos.MeetingDAO;

public class MeetingServiceImplementation implements MeetingService
{
    private final MeetingDAO meetingDAO;

    public MeetingServiceImplementation(MeetingDAO meetingDAO) {this.meetingDAO = meetingDAO;}
}
