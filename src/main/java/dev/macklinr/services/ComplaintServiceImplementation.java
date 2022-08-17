package dev.macklinr.services;

import dev.macklinr.daos.ComplaintDAO;
import dev.macklinr.entities.Complaint;

import java.util.List;
import java.util.stream.Collectors;

public class ComplaintServiceImplementation implements ComplaintService
{
    private final ComplaintDAO complaintDAO;

    public ComplaintServiceImplementation(ComplaintDAO complaintDAO) {this.complaintDAO = complaintDAO;}

    @Override
    public Complaint registerComplaint(Complaint complaint)
    {
        return this.complaintDAO.createComplaint(complaint);
    }

    @Override
    public List<Complaint> getAllComplaints() {
        return this.complaintDAO.getAllComplaints();
    }

    @Override
    public List<Complaint> getAllComplaintsForMeeting(int meetingId)
    {
        return getAllComplaints().stream().filter(complaint -> complaint.getMeetingID() == meetingId).collect(Collectors.toList());
    }
}
