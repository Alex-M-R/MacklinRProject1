package dev.macklinr.daotests;

import dev.macklinr.daos.ComplaintDAO;
import dev.macklinr.daos.ComplaintDaoDB;
import dev.macklinr.entities.Complaint;
import dev.macklinr.entities.Priority;
import dev.macklinr.utils.ConnectionUtil;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ComplaintTests
{
    private static final String complaintTable = "testComplaint";
    ComplaintDAO complaintDAO = new ComplaintDaoDB(complaintTable);


    @BeforeAll
    static void setup()
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "create table " + complaintTable + "\n" +
                    "(\n" +
                    "id serial primary key,\n" +
                    "description varchar(200),\n" +
                    "status varchar(40) check (status in ('UNREVIEWED', 'HIGH', 'LOW', 'RESOLVED', 'IGNORED')) default 'UNREVIEWED',\n" +
                    "meeting_id int references meeting(id) default -1\n" +
                    ");";

            Statement statement = conn.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void create_complete_test()
    {
        Complaint newComplaint = new Complaint("I'm a test complaint", Priority.UNREVIEWED, -1);

        Complaint storedComplaint = this.complaintDAO.createComplaint(newComplaint);

        Assertions.assertEquals("I'm a test complaint", storedComplaint.getDescription());
    }

    @Test
    @Order(2)
    void get_all_complaints_test()
    {
        List<Complaint> complaintList = complaintDAO.getAllComplaints();

        Assertions.assertEquals(1, complaintList.size());
    }


    @Test
    @Order(3)
    void get_complaint_by_id_test()
    {
       Complaint existing = complaintDAO.getComplaintByID(1);

       Assertions.assertEquals(1, existing.getId());
    }


    @Test
    @Order(4)
    void update_complaint_test()
    {
        Complaint existing = complaintDAO.getComplaintByID(1);

        existing.setDescription("I'm still a test complaint!");

        Complaint storedComplaint = complaintDAO.updateComplaint(existing);

        Assertions.assertEquals("I'm still a test complaint!", storedComplaint.getDescription());
    }

    @AfterAll
    static void teardown()
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "drop table " + complaintTable + ";";

            Statement statement = conn.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
