package wallet_service.out.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import wallet_service.in.model.Action;
import wallet_service.out.service.PlayerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HistoryControllerTest {

    @Mock
    private PlayerService playerService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Test
    @DisplayName("Test Get Player History")
    public void testGetPlayerHistory() throws Exception {
        MockitoAnnotations.openMocks(this);

        List<Action> actions = Arrays.asList(
                new Action("user1", "action1", "details1"),
                new Action("user1", "action2", "details2")
        );
        when(playerService.getPlayerActions("user1")).thenReturn(actions);

        when(request.getParameter("username")).thenReturn("user1");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        HistoryController historyController = new HistoryController(playerService);
        historyController.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).getWriter();
        writer.flush();


        String jsonResponse = stringWriter.toString().trim();
        assertEquals("[{\"username\":\"user1\",\"action\":\"action1\",\"detail\":\"details1\"},{\"username\":\"user1\",\"action\":\"action2\",\"detail\":\"details2\"}]", jsonResponse);
    }
}