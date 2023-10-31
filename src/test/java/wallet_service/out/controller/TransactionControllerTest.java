package wallet_service.out.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wallet_service.out.model.Transaction;
import wallet_service.out.service.PlayerServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PlayerServiceImpl playerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new TransactionController(playerService, null, null)).build();
    }


    @Test
    @DisplayName("testDebitTransaction")
    public void testDebitTransaction() throws Exception {
        String expectedResponse = "Дебетовая транзакция успешно выполнена!";

        doNothing().when(playerService).debit("username", 1, 50.00);

        mockMvc.perform(post("/debit")
                        .param("username", "username")
                        .param("id", "1")
                        .param("amount", "50.00"))
                .andExpect(status().isOk());

        verify(playerService, times(1)).debit("username", 1, 50.00);
    }

    @Test
    @DisplayName("testCreditTransaction")
    public void testCreditTransaction() throws Exception {
        String expectedResponse = "Кредитная транзакция успешно выполнена!";

        doNothing().when(playerService).credit("username", 1, 50.00);

        mockMvc.perform(post("/credit")
                        .param("username", "username")
                        .param("id", "1")
                        .param("amount", "50.00"))
                .andExpect(status().isOk());

        verify(playerService, times(1)).credit("username", 1, 50.00);
    }

    @Test
    @DisplayName("testGetTransactionHistory")
    public void testGetTransactionHistory() throws Exception {
        List<Transaction> expectedTransactions = Collections.singletonList(new Transaction());
        when(playerService.getTransactionHistory("username")).thenReturn(expectedTransactions);

        mockMvc.perform(get("/transactions/username"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(playerService, times(1)).getTransactionHistory("username");
    }
}