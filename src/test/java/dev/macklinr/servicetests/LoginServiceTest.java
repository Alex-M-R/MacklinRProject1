package dev.macklinr.servicetests;

import dev.macklinr.daos.UserDAO;
import dev.macklinr.entities.Role;
import dev.macklinr.entities.User;
import dev.macklinr.exceptions.NoUserFoundException;
import dev.macklinr.exceptions.PasswordMismatchException;
import dev.macklinr.services.LoginService;
import dev.macklinr.services.LoginServiceImplementation;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginServiceTest
{
    private final UserDAO uDAO = Mockito.mock(UserDAO.class);

    private final LoginService lService = new LoginServiceImplementation(uDAO);

    @Test
    @Order(1)
    void validate_user_test()
    {
        Mockito.when(uDAO.getUserByUserName("testuser")).thenReturn(new User(1, "test", "user", "testuser", "password", Role.CONSTITUENT));
        User user = lService.validateUser("testuser", "password");

        Assertions.assertEquals(1, user.getId());
    }

    @Test
    @Order(2)
    void username_wrong_test()
    {
        Mockito.when(uDAO.getUserByUserName("testuser")).thenReturn(null);
      Assertions.assertThrows(NoUserFoundException.class, () -> { lService.validateUser("testuser", "password");});
    }

    @Test
    @Order(3)
    void password_wrong_test()
    {
        Mockito.when(uDAO.getUserByUserName("testuser")).thenReturn(new User(1, "test", "user", "testuser", "password", Role.CONSTITUENT));
        Assertions.assertThrows(PasswordMismatchException.class, () -> {lService.validateUser("testuser", "wrongpassword");});
    }

}
