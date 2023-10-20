package wallet_service.in.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import wallet_service.in.repository.PlayerRepository;
import wallet_service.in.repository.TransactionRepository;
import wallet_service.in.service.PlayerService;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {
    @Mock
    private PlayerService playerService;

    @InjectMocks
    private TransactionController transactionController;

    public TransactionControllerTest() {
    }

    @Before
    public void setUp() throws Exception {
        doNothing().when(playerService).debit(anyString(), anyInt(), anyDouble());
        doNothing().when(playerService).credit(anyString(), anyInt(), anyDouble());

    }

    @Test
    @DisplayName("Test Debit Transaction")
    public void testDebitTransaction() throws Exception {
        transactionController.debitTransaction("testUser", 1, 1000);
        verify(playerService, times(1)).debit("testUser", 1, 1000);
    }

    @Test
    @DisplayName("Test Credit Transaction")
    public void testCreditTransaction() throws Exception {
        transactionController.creditTransaction("testUser", 1, 1000);
        verify(playerService, times(1)).credit("testUser", 1, 1000);
    }
}
