package wallet_service.in.service;

import org.junit.Before;
import org.junit.Test;
import wallet_service.in.controller.TransactionType;
import wallet_service.in.model.Action;
import wallet_service.in.model.Player;
import wallet_service.in.model.Transaction;
import wallet_service.in.service.PlayerService;
import wallet_service.out.repository.PlayerRepository;
import wallet_service.out.repository.TransactionRepository;

import java.util.List;

import static org.junit.Assert.*;

public class PlayerServiceTest {

    private PlayerService playerService;
    private PlayerRepository playerRepository;
    private TransactionRepository transactionRepository;

    @Before
    public void setUp() {
        playerRepository = new PlayerRepository();
        transactionRepository = new TransactionRepository();
        playerService = new PlayerService(playerRepository, transactionRepository);
    }

    /**
     * addAction() и testGetPlayerActions() Проверяют, что действие корректно добавляется для игрока
     * и возвращается при вызове getPlayerActions().
     */
    @Test
    public void addAction() {
        playerService.addAction("username", "action", "detail");

        List<Action> actions = playerService.getPlayerActions("username");

        assertEquals(1, actions.size());
        assertEquals("username", actions.get(0).getUsername());
        assertEquals("action", actions.get(0).getAction());
        assertEquals("detail", actions.get(0).getDetail());
    }

    @Test
    public void testGetPlayerActions() {
        playerService.addAction("username", "action1", "detail1");
        playerService.addAction("username", "action2", "detail2");

        List<Action> actions = playerService.getPlayerActions("username");

        assertEquals(2, actions.size());
        assertEquals("action1", actions.get(0).getAction());
        assertEquals("action2", actions.get(1).getAction());
        assertEquals("detail1", actions.get(0).getDetail());
        assertEquals("detail2", actions.get(1).getDetail());
    }

    /**
     * checkingUsernameThereAreNoActionsInTheSystem() Проверяет, что для нового пользователя вернется пустой список
     * действий.
     */
    @Test
    public void checkingUsernameThereAreNoActionsInTheSystem() {
        List<Action> actions = playerService.getPlayerActions("username");

        assertNotNull(actions);
        assertEquals(0, actions.size());
    }

    /**
     * testRegisterPlayer() и testAuthenticatePlayer() Проверяют процедуру регистрации и аутентификации пользователя.
     *
     */
    @Test
    public void testRegisterPlayer() {
        PlayerRepository playerRepository = new PlayerRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        PlayerService playerService = new PlayerService(playerRepository, transactionRepository);

        playerService.registerPlayer("username", "password");

        assertNotNull(playerRepository.getPlayer("username"));
    }

    @Test
    public void testAuthenticatePlayer() {
        PlayerRepository playerRepository = new PlayerRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        PlayerService playerService = new PlayerService(playerRepository, transactionRepository);
        playerService.registerPlayer("username", "password");

        boolean authenticated = playerService.authenticatePlayer("username", "password");

        assertTrue(authenticated);
    }
    /**
     * TestAuthenticatePlayer_invalidUsername() и testAuthenticatePlayer_invalidPassword() Проверяют,
     * что процедура аутентификации возвращает false при неправильном имени пользователя или пароле.
     */
    @Test
    public void testAuthenticatePlayer_invalidUsername() {
        boolean authenticated = playerService.authenticatePlayer("username", "password");

        assertFalse(authenticated);
    }

    @Test
    public void testAuthenticatePlayer_invalidPassword() {
        playerRepository.addPlayer(new Player("username", "password"));

        boolean authenticated = playerService.authenticatePlayer("username", "wrongPassword");

        assertFalse(authenticated);
    }

    /**
     * testGetBalance() Проверяет, что баланс игрока корректно обновляется после проведения транзакций.
     */
    @Test
    public void testGetBalance() throws Exception {
        PlayerRepository playerRepository = new PlayerRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        PlayerService playerService = new PlayerService(playerRepository, transactionRepository);
        playerService.registerPlayer("username", "password");
        playerService.credit("username", "transactionId", 100.0);

        double balance = playerService.getBalance("username");

        assertEquals(100.0, balance, 0.0);
    }

    /**
     * debit() и credit() Эти тесты проверяют, что транзакции дебета и кредита корректно обновляют баланс игрока
     * и добавляют транзакции в список транзакций.
     */
    @Test
    public void debit() throws Exception {
        playerRepository.addPlayer(new Player("username", "password"));
        playerService.credit("username", "transactionId1", 100.0);

        playerService.debit("username", "transactionId2", 50.0);

        Player player = playerRepository.getPlayer("username");
        assertEquals(50.0, player.getBalance(), 0.0);
        assertEquals(2, player.getTransactions().size());
        assertEquals(TransactionType.DEBIT, player.getTransactions().get(1).getType());
    }

    /**
     * debit_insufficientBalance() и debit_nonexistentUser_shouldThrowException()
     * credit_nonexistentUser_shouldThrowException() и testCredit_invalidUsername()
     * Эти тесты проверяют различные случаи, когда должно вызываться исключение.
     */
    @Test
    public void debit_insufficientBalance() {
        playerRepository.addPlayer(new Player("username", "password"));

        try {
            playerService.debit("username", "transactionId", 50.0);
            fail("Expected an Exception to be thrown");
        } catch (Exception e) {
            assertEquals("Недостаточно средств", e.getMessage());
        }

        Player player = playerRepository.getPlayer("username");
        assertEquals(0.0, player.getBalance(), 0.0);

    }

    @Test
    public void debit_nonexistentUser_shouldThrowException() {
        try {
            playerService.debit("nonexistentUsername", "transactionId", 50.0);
            fail("Expected an Exception to be thrown");
        } catch (Exception e) {
            assertEquals("Игрок не найден", e.getMessage());
        }
    }

    @Test
    public void credit_nonexistentUser_shouldThrowException() {
        try {
            playerService.credit("nonexistentUsername", "transactionId", 50.0);
            fail("Expected an Exception to be thrown");
        } catch (Exception e) {
            assertEquals("Игрок не найден", e.getMessage());
        }
    }


    @Test
    public void credit() throws Exception {
        playerRepository.addPlayer(new Player("username", "password"));

        playerService.credit("username", "transactionId", 100.0);

        Player player = playerRepository.getPlayer("username");
        assertEquals(100.0, player.getBalance(), 0.0);
        assertEquals(1, player.getTransactions().size());
        assertEquals(TransactionType.CREDIT, player.getTransactions().get(0).getType());
    }

    @Test
    public void testCredit_invalidUsername() {
        try {
            playerService.credit("username", "transactionId", 100.0);
            fail("Expected an Exception to be thrown");
        } catch (Exception e) {
            assertEquals("Игрок не найден", e.getMessage());
        }
    }

    /**
     * getTransactionHistory() Проверяет, что история транзакций игрока возвращается корректно.
     */
    @Test
    public void getTransactionHistory() throws Exception {
        playerRepository.addPlayer(new Player("username", "password"));
        playerService.credit("username", "transactionId1", 100.0);
        playerService.debit("username", "transactionId2", 50.0);

        List<Transaction> transactions = playerService.getTransactionHistory("username");

        assertEquals(2, transactions.size());
        assertEquals(TransactionType.CREDIT, transactions.get(0).getType());
        assertEquals(TransactionType.DEBIT, transactions.get(1).getType());
    }

    /**
     * logout() Проверяет, что при выходе игрока создается соответствующее действие.
     */
    @Test
    public void logout() {
        playerService.logout("username");

        List<Action> actions = playerService.getPlayerActions("username");

        assertEquals(1, actions.size());
        assertEquals("username", actions.get(0).getUsername());
        assertEquals("Выход из системы", actions.get(0).getAction());
        assertEquals("", actions.get(0).getDetail());
    }
}