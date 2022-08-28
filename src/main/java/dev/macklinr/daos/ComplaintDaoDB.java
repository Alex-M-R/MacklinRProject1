package dev.macklinr.daos;

import dev.macklinr.entities.Complaint;
import dev.macklinr.entities.Priority;
import dev.macklinr.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDaoDB implements ComplaintDAO
{
    String tableName;

    public ComplaintDaoDB()
    {
        super();
        tableName = "complaint";  // default name
    }

    public ComplaintDaoDB(String tableName) {this.tableName = tableName;}

    @Override
    public Complaint createComplaint(Complaint complaint)
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "insert into " + this.tableName + " values (default, ?, default, default)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, complaint.getDescription());
            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys(); // returns the id that was created
            rs.next();   // have to move cursor to the first valid record

            int generatedKey = rs.getInt("id");

            complaint.setId(generatedKey);
            return complaint;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Complaint> getAllComplaints()
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "select * from " + this.tableName;

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            List<Complaint> complaintsList = new ArrayList();
            while(rs.next())
            {
                Complaint complaint = new Complaint();

                complaint.setId(rs.getInt("id"));
                complaint.setDescription(rs.getString("description"));
                complaint.setStatus(Priority.valueOf(rs.getString("status")));
                complaint.setMeetingID(rs.getInt("meeting_id"));

                complaintsList.add(complaint);
            }

            return complaintsList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Complaint getComplaintByID(int id)
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "select * from " + this.tableName + " where id= ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,id);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            Complaint complaint = new Complaint();

            complaint.setId(rs.getInt("id"));
            complaint.setDescription(rs.getString("description"));
            complaint.setStatus(Priority.valueOf(rs.getString("status")));
            complaint.setMeetingID(rs.getInt("meeting_id"));


            return complaint;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Complaint updateComplaint(Complaint complaint)
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "update " + this.tableName + " set meeting_id = ?, description = ?, status = ? where id= ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, complaint.getMeetingID());
            preparedStatement.setString(2, complaint.getDescription());
            preparedStatement.setString(3, complaint.getStatus().name());
            preparedStatement.setInt(4, complaint.getId());

            preparedStatement.executeUpdate();

            return complaint;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
