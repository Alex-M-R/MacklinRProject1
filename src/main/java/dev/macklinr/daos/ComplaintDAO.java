package dev.macklinr.daos;

import dev.macklinr.entities.Complaint;

import java.util.List;

public interface ComplaintDAO
{
    Complaint createComplaint(Complaint complaint);

    List<Complaint> getAllComplaints();

    Complaint getComplaintByID(int id);
    Complaint updateComplaint(Complaint complaint);
}
