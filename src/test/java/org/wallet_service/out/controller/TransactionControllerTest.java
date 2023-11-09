package test.java.org.wallet_service.out.controller;

import main.java.org.wallet_service.WalletServiceApplication;
import main.java.org.wallet_service.out.model.Transaction;
import main.java.org.wallet_service.out.service.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = WalletServiceApplication.class)
class TransactionControllerTest {

    private MockMvc mvc;

    @MockBean
    private PlayerServiceImpl playerService;

    @Autowired
    private main.java.org.wallet_service.out.controller.TransactionController transactionController;

    @BeforeEach
    void setUp() throws Exception {
        mvc = MockMvcBuilders
                .standaloneSetup(transactionController)
                .defaultRequest(MockMvcRequestBuilders.get("").characterEncoding("UTF-8"))
                .build();
    }

    Transaction transaction1 = new Transaction("user1", 1, "user1", 150);
    Transaction transaction2 = new Transaction("user2", 1, "user2", -50);
    List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

    @Test
    void debitTransaction() throws Exception {
        doNothing().when(playerService).debit(anyString(), anyInt(), anyDouble());
        mvc.perform(post("/debit")
                        .param("username", "user1")
                        .param("id", "1")
                        .param("amount", "150.0")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().string("Дебетовая транзакция успешно выполнена!"));

        verify(playerService, times(1)).debit("user1", 1, 150.0);
    }

    @Test
    void creditTransaction() throws Exception {
        doNothing().when(playerService).credit(anyString(), anyInt(), anyDouble());
        mvc.perform(post("/credit")
                        .param("username", "user1")
                        .param("id", "1")
                        .param("amount", "150.0")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().string("Кредитная транзакция успешно выполнена!"));

        verify(playerService, times(1)).credit("user1", 1, 150.0);
    }

    @Test
    void getTransactionHistory() throws Exception {
        mvc.perform(get("/transaction/history")
                        .param("username", "user1")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk());

        verify(playerService, times(1)).getTransactionHistory("user1");
    }
}