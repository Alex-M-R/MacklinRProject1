package dev.macklinr.daos;

import dev.macklinr.entities.User;
import dev.macklinr.utils.ConnectionUtil;

import java.sql.*;

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
            // insert into app_user values (-1, 'UNREGISTERED_USER', 'password', 'CONSTITUENT');
            String sql = "insert into " + this.tableName + " values (default, ?, ?, ?);";

            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3,user.getRole().name());

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
}
