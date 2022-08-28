package dev.macklinr.daos;

import dev.macklinr.entities.Role;
import dev.macklinr.entities.User;
import dev.macklinr.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoDB implements UserDAO
{
    String tableName;

    public UserDaoDB()
    {
        super();
        tableName = "app_user";  // default name
    }

    public UserDaoDB(String tableName) {this.tableName = tableName;}

    @Override
    public User createUser(User user)
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "insert into " + this.tableName + " values (default, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getfName());
            preparedStatement.setString(2, user.getlName());
            preparedStatement.setString(3,user.getUsername());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.setString(5, user.getRole().name());

            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();

            int generatedKey = rs.getInt("id");

            user.setId(generatedKey);
            return user;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUserByUserName(String username)
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "select * from " + this.tableName + " where username = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            User user = new User();

            user.setId(rs.getInt("id"));
            user.setfName(rs.getString("fname"));
            user.setlName(rs.getString("lname"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(Role.valueOf(rs.getString("role")));

            return user;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "select * from " + this.tableName;

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            List<User> userList = new ArrayList();
            while(rs.next())
            {
                User user = new User();

                user.setId(rs.getInt("id"));
                user.setfName(rs.getString("fname"));
                user.setlName(rs.getString("lname"));
                user.setUsername(rs.getString("username"));
                user.setPassword("null");
                user.setRole(Role.valueOf(rs.getString("role")));

                userList.add(user);
            }

            return userList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean setRole(int id, Role newRole)
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "update " + this.tableName + " set role = ? where id= ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, newRole.name());
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
