package dev.macklinr.services;

import dev.macklinr.entities.Complaint;
import dev.macklinr.entities.Priority;

import java.util.List;

public interface ComplaintService
{
    Complaint registerComplaint(Complaint complaint);

    List<Complaint> getAllComplaints();

    List<Complaint> getAllComplaintsForMeeting(int meetingId);

    Complaint getComplaintByID(int id);
    Complaint updateComplaintStatus(int id, Priority newStatus);
}
