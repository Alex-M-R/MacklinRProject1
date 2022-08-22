package dev.macklinr.daotests;

import dev.macklinr.daos.UserDAO;
import dev.macklinr.daos.UserDaoDB;
import dev.macklinr.entities.Role;
import dev.macklinr.entities.User;
import dev.macklinr.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTests
{
    private static final String userTable = "testUser";
    UserDAO userDAO = new UserDaoDB(userTable);

    @BeforeAll
    static void setup()
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "create table " + userTable + "\n" +
                    "(\n" +
                    "id serial primary key,\n" +
                    "username varchar(40),\n" +
                    "password varchar(40),\n" +
                    "role varchar(40) check (role in ('CONSTITUENT', 'COUNCIL'))\n" +
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
    void create_user_test()
    {
        User user = userDAO.createUser(new User(0,"testUser","password" ,Role.COUNCIL));
    }


    // Test cases pending ERD approval
    @Test
    @Order(2)
    void get_user_by_username_test()
    {
        User user = userDAO.getUserByUserName("testUser");
        Assertions.assertEquals("testUser", user.getUsername());
    }




    @AfterAll
    static void teardown()
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "drop table " + userTable + ";";

            Statement statement = conn.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
