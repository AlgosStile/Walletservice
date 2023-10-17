//package wallet_service.in.controller;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import wallet_service.in.service.PlayerService;
//
//import java.sql.SQLException;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//public class TransactionControllerTest {
//
//    private PlayerService playerService;
//    private TransactionController transactionController;
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        playerService = mock(PlayerService.class);
//        transactionController = new TransactionController(playerService, playerRepository, transactionController);
//    }
//
//    @Test
//    @DisplayName("Debit Transaction Test")
//    public void debitTransactionTest() throws Exception {
//        String username = "testUser";
//        int transactionId = Integer.parseInt("testTransaction");
//        double amount = 100.0;
//
//        transactionController.debitTransaction(username, transactionId, amount);
//
//        verify(playerService).debit(username,  transactionId, amount);
//    }
//
//    @Test
//    @DisplayName("Credit Transaction Test")
//    public void creditTransactionTest() throws Exception {
//        String username = "testUser";
//        int transactionId = Integer.parseInt("testTransaction");
//        double amount = 100.0;
//
//        transactionController.creditTransaction(username, transactionId, amount);
//
//        verify(playerService).credit(username, transactionId, amount);
//    }
//
//    @Test
//    @DisplayName("Get Transaction History Test")
//    public void getTransactionHistoryTest() throws SQLException {
//        String username = "testUser";
//
//        transactionController.getTransactionHistory(username);
//
//        verify(playerService).getTransactionHistory(username);
//    }
//}
//
//
//
