package dev.macklinr.servicetests;

import dev.macklinr.daos.ComplaintDAO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComplaintServiceTests
{
    private final ComplaintDAO cDAO = Mockito.mock(ComplaintDAO.class);




}