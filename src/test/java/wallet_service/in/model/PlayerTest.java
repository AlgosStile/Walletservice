package wallet_service.in.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wallet_service.in.controller.TransactionType;
import wallet_service.in.repository.PlayerRepository;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private PlayerRepository playerRepository;
    private Player player;

    @BeforeEach
    public void setUp() throws SQLException {
        playerRepository = new PlayerRepository();
        player = new Player("username", "password");
        playerRepository.addPlayer(player);
    }

    @Test
    @DisplayName("Debit Negative Amount Should Throw Exception Test")
    public void Debit_negativeAmount_shouldThrowExceptionTest() {
        assertThrows(Exception.class, () -> player.debit(Integer.parseInt("transactionId"), -50.0));
    }

    @Test
    @DisplayName("Credit Negative Amount Should Throw Exception Test")
    public void Credit_negativeAmount_shouldThrowExceptionTest() {
        assertThrows(Exception.class, () -> player.credit(Integer.parseInt("transactionId"), -50.0));
    }

    @Test
    @DisplayName("Get Username Test")
    public void GetUsernameTest() {
        Player player = new Player("username", "password");

        String username = player.getUsername();

        assertEquals("username", username);
    }

    @Test
    @DisplayName("Get Password Test")
    public void getPasswordTest() {
        Player player = new Player("username", "password");

        String password = player.getPassword();

        assertEquals("password", password);
    }

    @Test
    @DisplayName("Get Balance Test")
    public void GetBalanceTest() throws Exception {
        Player player = new Player("username", "password");
        player.credit(Integer.parseInt("transactionId"), 100.0);

        double balance = player.getBalance();

        assertEquals(100.0, balance, 0.0);
    }

    @Test
    @DisplayName("Debit Insufficient Balance Should Not Change Balance Test")
    public void Debit_insufficientBalance_shouldNotChangeBalanceTest() throws SQLException {
        Player player = new Player("username", "password");

        assertThrows(Exception.class, () -> player.debit(Integer.parseInt("transactionId"), 50.0));

        assertEquals(0.0, player.getBalance(), 0.0);
        assertTrue(player.getTransactions().isEmpty());
    }

    @Test
    @DisplayName("Get Transactions Test")
    public void GetTransactionsTest() {
        Player player = new Player("username", "password");

        assertNotNull(player.getTransactions());
        assertEquals(0, player.getTransactions().size());
    }


    @Test
    @DisplayName("Debit Test")
    public void DebitTest() throws Exception {
        Player player = new Player("username", "password");
        player.credit(Integer.parseInt("transactionId"), 100.0);

        player.debit(Integer.parseInt("transactionId"), 50.0);

        assertEquals(50.0, player.getBalance(), 0.0);
        assertEquals(2, player.getTransactions().size());
        assertEquals(TransactionType.DEBIT, player.getTransactions().get(1).getType());
    }

    @Test
    @DisplayName("Credit Test")
    public void creditTest() throws Exception {
        Player player = new Player("username", "password");

        player.credit(Integer.parseInt("transactionId"), 100.0);

        assertEquals(100.0, player.getBalance(), 0.0);
        assertEquals(1, player.getTransactions().size());
        assertEquals(TransactionType.CREDIT, player.getTransactions().get(0).getType());
    }

    @Test
    @DisplayName("Credit Negative Amount Test")
    public void credit_negativeAmountTest() throws SQLException {
        Player player = new Player("username", "password");

        assertThrows(Exception.class, () -> player.credit(Integer.parseInt("transactionId"), -100.0));

        assertEquals(0.0, player.getBalance(), 0.0);
    }

    @Test
    @DisplayName("Credit Zero Amount Test")
    public void credit_zeroAmountTest() throws Exception {
        Player player = new Player("username", "password");

        player.credit(Integer.parseInt("transactionId"), 0.0);

        assertEquals(0.0, player.getBalance(), 0.0);
        assertEquals(1, player.getTransactions().size());
        assertEquals(TransactionType.CREDIT, player.getTransactions().get(0).getType());
    }

    @Test
    @DisplayName("Debit Insufficient Balance Test")
    public void Debit_insufficientBalanceTest() throws SQLException {
        Player player = new Player("username", "password");

        assertThrows(Exception.class, () -> player.debit(Integer.parseInt("transactionId"), 50.0));

        assertEquals(0.0, player.getBalance(), 0.0);
    }
}
