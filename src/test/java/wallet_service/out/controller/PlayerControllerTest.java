package wallet_service.out.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import wallet_service.out.service.PlayerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerControllerTest {
    @Mock
    private PlayerService playerService;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @InjectMocks
    private PlayerController playerController;

    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Test get player")
    public void testGetPlayer() throws IOException, ServletException {
        setup();

        when(req.getParameter("username")).thenReturn("test");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(resp.getWriter()).thenReturn(writer);

        playerController.doGet(req, resp);

        writer.flush();
        String responseString = stringWriter.toString();

    }


    @Test
    @DisplayName("Test delete player")
    public void testDeletePlayer() throws IOException, ServletException {
        setup();

        when(req.getReader()).thenReturn(mock(BufferedReader.class));

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(resp.getWriter()).thenReturn(writer);

        playerController.doDelete(req, resp);


        writer.flush();
        String responseString = stringWriter.toString();

    }
}