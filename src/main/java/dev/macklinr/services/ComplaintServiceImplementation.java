package dev.macklinr.services;

import dev.macklinr.daos.ComplaintDAO;
import dev.macklinr.entities.Complaint;
import dev.macklinr.entities.Priority;

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

    @Override
    public Complaint getComplaintByID(int id)
    {
        return this.complaintDAO.getComplaintByID(id);
    }

    @Override
    public Complaint updateComplaintStatus(int id, Priority newStatus)
    {
       Complaint existing = this.complaintDAO.getComplaintByID(id);
       existing.setStatus(newStatus);
       return this.complaintDAO.updateComplaint(existing);
    }

    @Override
    public Complaint updateComplaint(Complaint complaint)
    {
        return this.complaintDAO.updateComplaint(complaint);
    }
}
