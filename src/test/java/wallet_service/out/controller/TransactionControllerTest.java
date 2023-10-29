package wallet_service.out.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import wallet_service.out.service.PlayerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.mockito.Mockito.when;

public class TransactionControllerTest {

    @Test
    @DisplayName("Always pass test for TransactionController")
    public void doPostTest() throws Exception {
        // Prepare test data and mock objects
        String username = "test";
        String transactionId = "123";
        double amount = 100.0;
        String json = "{\"username\":\"" + username + "\",\"id\":\"" + transactionId + "\",\"amount\":" + amount + "}";
        BufferedReader reader = new BufferedReader(new StringReader(json));
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        PlayerService playerService = Mockito.mock(PlayerService.class);
        ObjectMapper objectMapper = new ObjectMapper();

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);

        TransactionController transactionController = new TransactionController(playerService);
        transactionController.doPost(request, response);

        // no exception was thrown.
    }
}
