package dev.macklinr.smoketests;

import dev.macklinr.utils.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionTests
{
    @Test
    void connection_available()
    {
        Connection connection = ConnectionUtil.createConnection();
        Assertions.assertNotNull(connection);
    }
}
