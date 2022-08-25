package dev.macklinr.servicetests;

import dev.macklinr.daos.ComplaintDAO;
import dev.macklinr.entities.Complaint;
import dev.macklinr.entities.Priority;
import dev.macklinr.services.ComplaintService;
import dev.macklinr.services.ComplaintServiceImplementation;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComplaintServiceTests
{
    private final ComplaintService cService = new ComplaintServiceImplementation(Mockito.mock(ComplaintDAO.class));


    @Test
    @Order(1)
    void create_complaint_service_test()
    {
        Complaint newComplaint = new Complaint(1, "description", Priority.UNREVIEWED, -1);

        Mockito.when(cService.registerComplaint(newComplaint)).thenReturn(newComplaint);
        Complaint storedComplaint = cService.registerComplaint(newComplaint);

        Assertions.assertEquals("description", storedComplaint.getDescription());

        cService.getAllComplaints();
        cService.getComplaintByID(1);
        cService.updateComplaint(newComplaint);
        cService.getAllComplaintsForMeeting(1);
    }

    @Test
    @Order(2)
    void get_Complaint_By_ID()
    {
        Mockito.when(cService.getComplaintByID(1)).thenReturn(new Complaint(1, "description", Priority.UNREVIEWED, -1));

        Complaint existing = cService.getComplaintByID(1);

        Assertions.assertEquals(Priority.UNREVIEWED, existing.getStatus());
    }

    @Test
    @Order(3)
    void get_all_complaints_test()
    {
        List<Complaint> complaintList = new ArrayList();
        complaintList.add(new Complaint(1, "one", Priority.UNREVIEWED, 10));
        complaintList.add(new Complaint(2, "two", Priority.UNREVIEWED, 10));
        complaintList.add(new Complaint(3, "three", Priority.UNREVIEWED, 10));
        complaintList.add(new Complaint(4, "four", Priority.UNREVIEWED, 10));
        complaintList.add(new Complaint(5, "five", Priority.UNREVIEWED, 10));

        Mockito.when(cService.getAllComplaints()).thenReturn(complaintList);

        Assertions.assertEquals(5, cService.getAllComplaints().size());
    }

    @Test
    @Order(4)
    void update_complaint_test()
    {
        Complaint complaint = new Complaint(1, "one", Priority.UNREVIEWED, 10);

        Mockito.when(cService.updateComplaint(complaint)).thenReturn(complaint);

        complaint.setDescription("ten");

        Complaint updated = cService.updateComplaint(complaint);

        Assertions.assertEquals("ten", updated.getDescription());
    }

    @Test
    @Order(5)
    void get_all_complaints_for_meeting_test()
    {
        List<Complaint> complaintList = new ArrayList();
        complaintList.add(new Complaint(1, "one", Priority.UNREVIEWED, 10));
        complaintList.add(new Complaint(2, "two", Priority.UNREVIEWED, 10));
        complaintList.add(new Complaint(3, "three", Priority.UNREVIEWED, 10));
        complaintList.add(new Complaint(4, "four", Priority.UNREVIEWED, 10));
        complaintList.add(new Complaint(5, "five", Priority.UNREVIEWED, 10));

        Mockito.when(cService.getAllComplaintsForMeeting(10)).thenReturn(complaintList);

        List<Complaint> filteredList = cService.getAllComplaintsForMeeting(10);

        Assertions.assertEquals(5, filteredList.size());
    }



}