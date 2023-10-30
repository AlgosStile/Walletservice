package wallet_service.out.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wallet_service.out.model.Transaction;
import wallet_service.out.service.PlayerServiceImpl;

import java.util.Arrays;
import java.util.List;

import static java.lang.reflect.Array.get;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {
    @Mock
    private PlayerServiceImpl playerService;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testDebitTransaction_Success() throws Exception {
        String username = "testUser";
        int id = 1;
        double amount = 100.0;

        mockMvc.perform(post("/debit")
                        .param("username", username)
                        .param("id", String.valueOf(id))
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Дебетовая транзакция успешно выполнена!"));

        verify(playerService, times(1)).debit(username, id, amount);
    }

    @Test
    public void testDebitTransaction_Failure() throws Exception {
        String username = "testUser";
        int id = 1;
        double amount = 100.0;
        String errorMessage = "Ошибка при выполнении дебетовой транзакции";

        doThrow(new RuntimeException(errorMessage)).when(playerService).debit(username, id, amount);

        mockMvc.perform(post("/debit")
                        .param("username", username)
                        .param("id", String.valueOf(id))
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isBadRequest())
                .andExpect((ResultMatcher) content().string("Ошибка при выполнении дебетовой транзакции: " + errorMessage));

        verify(playerService, times(1)).debit(username, id, amount);
    }

    @Test
    public void testCreditTransaction_Success() throws Exception {
        String username = "testUser";
        int id = 1;
        double amount = 100.0;

        mockMvc.perform(post("/credit")
                        .param("username", username)
                        .param("id", String.valueOf(id))
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Кредитная транзакция успешно выполнена!"));

        verify(playerService, times(1)).credit(username, id, amount);
    }

    @Test
    public void testCreditTransaction_Failure() throws Exception {
        String username = "testUser";
        int id = 1;
        double amount = 100.0;
        String errorMessage = "Ошибка при выполнении кредитной транзакции";

        doThrow(new RuntimeException(errorMessage)).when(playerService).credit(username, id, amount);

        mockMvc.perform(post("/credit")
                        .param("username", username)
                        .param("id", String.valueOf(id))
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isBadRequest())
                .andExpect((ResultMatcher) content().string("Ошибка при выполнении кредитной транзакции: " + errorMessage));

        verify(playerService, times(1)).credit(username, id, amount);
    }

    @Test
    public void testGetTransactionHistory_Success() throws Exception {
        String username = "testUser";
        List<Transaction> transactions = Arrays.asList(new Transaction(), new Transaction());

        when(playerService.getTransactionHistory(username)).thenReturn(transactions);

        mockMvc.perform((RequestBuilder) get("/transactions/{username}", Integer.parseInt(username)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().json("[{}, {}]"));

        verify(playerService, times(1)).getTransactionHistory(username);
    }

    @Test
    public void testGetTransactionHistory_Failure() throws Exception {
        String username = "testUser";
        String errorMessage = "Ошибка при получении истории транзакций";

        when(playerService.getTransactionHistory(username)).thenThrow(new RuntimeException(errorMessage));

        mockMvc.perform((RequestBuilder) get("/transactions/{username}", Integer.parseInt(username)))
                .andExpect(status().isBadRequest())
                .andExpect((ResultMatcher) content().string("null"));

        verify(playerService, times(1)).getTransactionHistory(username);
    }
}