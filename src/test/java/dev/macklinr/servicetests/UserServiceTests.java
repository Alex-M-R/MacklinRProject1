package dev.macklinr.servicetests;

import dev.macklinr.daos.UserDAO;
import dev.macklinr.daos.UserDaoDB;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTests
{
    private final UserDAO uDAO = Mockito.mock(UserDAO.class);
}
