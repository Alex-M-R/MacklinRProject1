package dev.macklinr.services;

import dev.macklinr.daos.ComplaintDAO;
import dev.macklinr.entities.Complaint;

public class ComplaintServiceImplementation implements ComplaintService
{
    private final ComplaintDAO complaintDAO;

    public ComplaintServiceImplementation(ComplaintDAO complaintDAO) {this.complaintDAO = complaintDAO;}
}
