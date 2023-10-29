package wallet_service.out.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import wallet_service.in.controller.TransactionType;
import wallet_service.in.model.Transaction;
import wallet_service.out.dto.TransactionDto;
import wallet_service.out.mapper.TransactionMapper;
import wallet_service.out.service.PlayerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@DisplayName("TransactionHistoryController Test")
class TransactionHistoryControllerTest {
    @Mock
    private PlayerService playerService;

    @DisplayName("Test doGet")
    @Test
    void testDoGet() throws IOException, ServletException {
        // Arrange
        MockitoAnnotations.openMocks(this);
        TransactionHistoryController controller = new TransactionHistoryController(playerService);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        String username = "testuser";
        List<Transaction> transactions = Arrays.asList(
                new Transaction("1", 100.0, TransactionType.CREDIT),
                new Transaction("2", -50.0, TransactionType.DEBIT)
        );

        when(request.getParameter("username")).thenReturn(username);
        when(playerService.getTransactionHistory(username)).thenReturn(transactions);

        List<TransactionDto> transactionDto = transactions.stream()
                .map(TransactionMapper.INSTANCE::transactionToTransactionDto)
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        String transactionsJson = objectMapper.writeValueAsString(transactionDto);

        try {
            controller.doGet(request, response);
        } catch (Exception e) {

        }
        writer.flush();
    }
}
