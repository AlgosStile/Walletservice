package wallet_service.model;

import org.junit.Before;
import org.junit.Test;
import wallet_service.controller.TransactionType;
import wallet_service.repository.PlayerRepository;

import static org.junit.Assert.*;

public class PlayerTest {

    private PlayerRepository playerRepository;
    private Player player;

    @Before
    public void setUp() {
        playerRepository = new PlayerRepository();
        player = new Player("username", "password");
        playerRepository.addPlayer(player);
    }


    /**
     * testDebit_negativeAmount_shouldThrowException() и testCredit_negativeAmount_shouldThrowException()
     * Эти тесты проверяют, что при попытке снять или положить насчет отрицательную сумму вызывается исключение.
     */
    @Test
    public void testDebit_negativeAmount_shouldThrowException() {
        assertThrows(Exception.class, () -> player.debit("transactionId", -50.0));
    }


    @Test
    public void testCredit_negativeAmount_shouldThrowException() {
        assertThrows(Exception.class, () -> player.credit("transactionId", -50.0));
    }

    /**
     * testGetUsername() и getPassword() Проверяют, что методы getUsername() и getPassword() класса Player
     * возвращают правильное имя пользователя и пароль.
     */
    @Test
    public void testGetUsername() {
        Player player = new Player("username", "password");

        String username = player.getUsername();

        assertEquals("username", username);
    }

    @Test
    public void getPassword() {
        Player player = new Player("username", "password");

        String password = player.getPassword();

        assertEquals("password", password);
    }

    /**
     * testGetBalance() и testDebit_insufficientBalance_shouldNotChangeBalance() Эти тесты проверяют,
     * что баланс игрока корректно обновляется при выполнении операций дебетован и кредитован.
     */
    @Test
    public void testGetBalance() throws Exception {
        Player player = new Player("username", "password");
        player.credit("transactionId", 100.0);

        double balance = player.getBalance();

        assertEquals(100.0, balance, 0.0);
    }

    @Test
    public void testDebit_insufficientBalance_shouldNotChangeBalance() {
        Player player = new Player("username", "password");

        try {
            player.debit("transactionId", 50.0);
            fail("Expected an Exception to be thrown");
        } catch (Exception e) {
            assertEquals("Недостаточно средств", e.getMessage());
            assertEquals(0.0, player.getBalance(), 0.0);
            assertTrue(player.getTransactions().isEmpty());
        }
    }


    /**
     * testGetTransactions(), testAddTransaction(), testDebit() и credit() Эти тесты проверяют,
     * что транзакции добавляются в список транзакций игрока и что баланс игрока корректно обновляется
     * при выполнении этих транзакций.
     */
    @Test
    public void testGetTransactions() {
        Player player = new Player("username", "password");

        assertNotNull(player.getTransactions());
        assertEquals(0, player.getTransactions().size());
    }

    @Test
    public void testAddTransaction() {
        Player player = new Player("username", "password");
        Transaction transaction = new Transaction("id", 100.0, TransactionType.DEBIT);

        player.addTransaction(transaction);

        assertEquals(1, player.getTransactions().size());
        assertTrue(player.getTransactions().contains(transaction));
    }

    @Test
    public void testDebit() throws Exception {
        Player player = new Player("username", "password");
        player.credit("transactionId", 100.0);

        player.debit("transactionId", 50.0);

        assertEquals(50.0, player.getBalance(), 0.0);
        assertEquals(2, player.getTransactions().size());
        assertEquals(TransactionType.DEBIT, player.getTransactions().get(1).getType());
    }

    @Test
    public void credit() throws Exception {
        Player player = new Player("username", "password");

        player.credit("transactionId", 100.0);

        assertEquals(100.0, player.getBalance(), 0.0);
        assertEquals(1, player.getTransactions().size());
        assertEquals(TransactionType.CREDIT, player.getTransactions().get(0).getType());
    }

    @Test
    public void credit_negativeAmount() {
        Player player = new Player("username", "password");

        try {
            player.credit("transactionId", -100.0);
            fail("Expected an Exception to be thrown");
        } catch (Exception e) {
            assertEquals("Некорректная сумма", e.getMessage());
        }

        assertEquals(0.0, player.getBalance(), 0.0);
    }

    @Test
    public void credit_zeroAmount() throws Exception {
        Player player = new Player("username", "password");

        player.credit("transactionId", 0.0);

        assertEquals(0.0, player.getBalance(), 0.0);
        assertEquals(1, player.getTransactions().size());
        assertEquals(TransactionType.CREDIT, player.getTransactions().get(0).getType());
    }

    @Test
    public void testDebit_insufficientBalance() {
        Player player = new Player("username", "password");

        try {
            player.debit("transactionId", 50.0);
            fail("Expected an Exception to be thrown");
        } catch (Exception e) {
            assertEquals("Недостаточно средств", e.getMessage());
        }

        assertEquals(0.0, player.getBalance(), 0.0);
    }
}