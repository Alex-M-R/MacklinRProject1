package dev.macklinr.servicetests;

import dev.macklinr.daos.ComplaintDAO;
import dev.macklinr.daos.MeetingDAO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeetingServiceTests
{
    private final MeetingDAO mDAO = Mockito.mock(MeetingDAO.class);
}
