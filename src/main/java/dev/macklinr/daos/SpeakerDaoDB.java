package dev.macklinr.daos;

import dev.macklinr.entities.*;
import dev.macklinr.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpeakerDaoDB implements SpeakerDAO
{

    String tableName;
    String userTableName;
    String meetingTableName;

    public SpeakerDaoDB(String tableName, String userTableName, String meetingTableName) {
        this.tableName = tableName;
        this.userTableName = userTableName;
        this.meetingTableName = meetingTableName;
    }

    @Override
    public boolean createSpeakerRequest(int  meetingID, int userID) {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "insert into " + this.tableName + " values (default, ?, ?, default)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, meetingID);
            preparedStatement.setInt(2, userID);

            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys(); // returns the id that was created
            rs.next();   // have to move cursor to the first valid record

            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Speaker> getAllSpeakers() {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "select * from " + this.tableName +
                    " inner join " + this.userTableName + " on speaker_id = " + this.userTableName + ".id" +
                    " inner join " + this.meetingTableName + " on meeting_id = " + this.meetingTableName + ".id;";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            List<Speaker> speakersList = new ArrayList<Speaker>();
            while(rs.next())
            {
                Speaker speaker = new Speaker();

                speaker.setId(rs.getInt("id"));
                speaker.setMeetingID(rs.getInt("meeting_id"));
                speaker.setAppUserID(rs.getInt("speaker_id"));
                speaker.setState(RequestState.valueOf(rs.getString("status")));
                speaker.setfName(rs.getString("fname"));
                speaker.setlName(rs.getString("lname"));
                speaker.setUserName(rs.getString("username"));
                speaker.setAppUserRole(Role.valueOf(rs.getString("role")));
                speaker.setMeetingSummary(rs.getString("summary"));

                speakersList.add(speaker);
            }

            return speakersList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return new ArrayList<Speaker>();
        }
    }

    @Override
    public boolean updateSpeakerState(int id, RequestState state) {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "update " + this.tableName + " set status = ? where id = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

           preparedStatement.setString(1, state.name());
           preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
