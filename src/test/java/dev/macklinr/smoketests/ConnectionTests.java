package dev.macklinr.smoketests;

import dev.macklinr.utils.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.sql.Connection;
import java.util.*;

public class ConnectionTests
{
    @Test
    void connection_available()
    {
        Connection connection = ConnectionUtil.createConnection();
        Assertions.assertNotNull(connection);
    }
}
