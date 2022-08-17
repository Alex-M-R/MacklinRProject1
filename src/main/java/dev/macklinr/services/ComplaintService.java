package dev.macklinr.services;

import dev.macklinr.entities.Complaint;

import java.util.List;

public interface ComplaintService
{
    Complaint registerComplaint(Complaint complaint);

    List<Complaint> getAllComplaints();

    List<Complaint> getAllComplaintsForMeeting(int meetingId);
}
