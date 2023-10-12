package wallet_service.in.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import wallet_service.in.controller.TransactionType;
import wallet_service.in.model.Action;
import wallet_service.in.model.Player;
import wallet_service.in.model.Transaction;
import wallet_service.in.repository.PlayerRepository;

import wallet_service.in.repository.TransactionRepository;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.List;


public class PlayerServiceImplTest {

    private PlayerService playerService;
    private PlayerRepository playerRepository;

@BeforeEach
public void setUp() {
    playerRepository = mock(PlayerRepository.class);
    TransactionRepository transactionRepository = mock(TransactionRepository.class);
    playerService = new PlayerServiceImpl(playerRepository, transactionRepository);
    Player testPlayer = new Player("username", "password");
    when(playerRepository.getPlayer("username")).thenReturn(testPlayer);
    doNothing().when(playerRepository).addPlayer(any(Player.class));
}


    @Test
    @DisplayName("Is User Authenticated Test")
    public void isUserAuthenticated_shouldReturnFalseWhenUsernameDoesNotMatchAuthenticatedUser() {
        assertFalse(playerService.isUserAuthenticated("anotherUsername"));
    }

    @Test
    @DisplayName("Add Action Test")
    public void addActionTest() {
        playerService.addAction("username", "action", "detail");

        List<Action> actions = playerService.getPlayerActions("username");

        assertEquals(1, actions.size());
        assertEquals("username", actions.get(0).getUsername());
        assertEquals("action", actions.get(0).getAction());
        assertEquals("detail", actions.get(0).getDetail());
    }

    @Test
    @DisplayName("Get Player Actions Test")
    public void GetPlayerActionsTest() {
        playerService.addAction("username", "action1", "detail1");
        playerService.addAction("username", "action2", "detail2");

        List<Action> actions = playerService.getPlayerActions("username");

        assertEquals(2, actions.size());
        assertEquals("action1", actions.get(0).getAction());
        assertEquals("action2", actions.get(1).getAction());
        assertEquals("detail1", actions.get(0).getDetail());
        assertEquals("detail2", actions.get(1).getDetail());
    }

    @Test
    @DisplayName("Checking Username There Are No Actions In The System Test")
    public void checkingUsernameThereAreNoActionsInTheSystemTest() {
        List<Action> actions = playerService.getPlayerActions("username");

        assertNotNull(actions);
        assertEquals(0, actions.size());
    }

    @Test
    @DisplayName("Register Player Test")
    public void RegisterPlayerTest() {
        assertThrows(RuntimeException.class, () -> {
            playerService.registerPlayer("username", "password");
        });
    }


    @Test
    @DisplayName("Authenticate Player Test")
    public void authenticatePlayerTest() {
        PlayerServiceImpl playerService = new PlayerServiceImpl(mock(PlayerRepository.class), mock(TransactionRepository.class));

        playerService.registerPlayer("username", "password");

        boolean authenticated = playerService.authenticatePlayer("username", "wrongPassword"); // Здесь мы используем неправильный пароль
        assertFalse(authenticated);
    }



    @Test
    @DisplayName("Authenticate Player Invalid Username Test")
    public void authenticatePlayer_invalidUsernameTest() {
        boolean authenticated = playerService.authenticatePlayer("invalidUsername", "password");
        assertFalse(authenticated);
    }

    @Test
    @DisplayName("Authenticate Player Invalid Password Test")
    public void AuthenticatePlayer_invalidPasswordTest() {
        playerRepository.addPlayer(new Player("username", "password"));

        boolean authenticated = playerService.authenticatePlayer("username", "wrongPassword");

        assertFalse(authenticated);
    }

    @Test
    @DisplayName("Get Balance Test")
    public void getBalanceTest() throws Exception {

        playerRepository.addPlayer(new Player("username", "password"));
        playerService.credit("username", "transactionId", 100.0);
        double balance = playerService.getBalance("username");
        assertEquals(100.0, balance, 0.0);
    }


    @Test
    @DisplayName("Debit Test")
    public void debitTest() throws Exception {
        playerRepository.addPlayer(new Player("username", "password"));
        playerService.credit("username", "transactionId1", 100.0);

        playerService.debit("username", "transactionId2", 50.0);

        Player player = playerRepository.getPlayer("username");
        assertEquals(50.0, player.getBalance(), 0.0);
        assertEquals(2, player.getTransactions().size());
        assertEquals(TransactionType.DEBIT, player.getTransactions().get(1).getType());
    }

    @Test
    @DisplayName("Debit Insufficient Balance Test")
    public void debit_insufficientBalanceTest() {
        playerRepository.addPlayer(new Player("username", "password"));

        assertThrows(Exception.class, () -> playerService.debit("username", "transactionId", 50.0));

        Player player = playerRepository.getPlayer("username");
        assertEquals(0.0, player.getBalance(), 0.0);
    }

    @Test
    @DisplayName("Debit Nonexistent User Should Throw Exception Test")
    public void debit_nonexistentUser_shouldThrowExceptionTest() {
        assertThrows(Exception.class, () -> playerService.debit("nonexistentUsername", "transactionId", 50.0));
    }

    @Test
    @DisplayName("Credit Nonexistent User Should Throw Exception Test")
    public void credit_nonexistentUser_shouldThrowExceptionTest() {
        assertThrows(Exception.class, () -> playerService.credit("nonexistentUsername", "transactionId", 50.0));
    }

    @Test
    @DisplayName("Credit Test")
    public void creditTest() throws Exception {
        playerRepository.addPlayer(new Player("username", "password"));

        playerService.credit("username", "transactionId", 100.0);

        Player player = playerRepository.getPlayer("username");
        assertEquals(100.0, player.getBalance(), 0.0);
        assertEquals(1, player.getTransactions().size());
        assertEquals(TransactionType.CREDIT, player.getTransactions().get(0).getType());
    }

    @Test
    @DisplayName("Credit Invalid Username Test")
    public void Credit_invalidUsernameTest() throws Exception {
        when(playerRepository.getPlayer("username")).thenReturn(null);

        assertThrows(Exception.class, () -> playerService.credit("username", "transactionId", 100.0));
    }

    @Test
    @DisplayName("Is User Authenticated Returns True When Username Matches Authenticated User Test")
    public void IsUserAuthenticated_returnsTrue_whenUsernameMatchesAuthenticatedUserTest() {
        Player testPlayer = new Player("JohnDoe", "password"); // Создаем экземпляр testPlayer
        when(playerRepository.getPlayer("JohnDoe")).thenReturn(testPlayer); // Возвращаем testPlayer при вызове getPlayer("JohnDoe")
        playerService.authenticatePlayer("JohnDoe", "password");

        boolean result = playerService.isUserAuthenticated("JohnDoe");
        assertTrue(result);
    }

    @Test
    @DisplayName("Is User Authenticated Returns False When Username Does Not Match Authenticated User Test")
    public void IsUserAuthenticated_returnsFalse_whenUsernameDoesNotMatchAuthenticatedUserTest() {
        Player player = new Player("JohnDoe", "password");
        playerRepository.addPlayer(player);
        playerService.authenticatePlayer("JohnDoe", "password");

        boolean result = playerService.isUserAuthenticated("JaneSmith");
        assertFalse(result);
    }

    @Test
    @DisplayName("Get Transaction History Test")
    public void getTransactionHistoryTest() throws Exception {
        playerRepository.addPlayer(new Player("username", "password"));
        playerService.credit("username", "transactionId1", 100.0);
        playerService.debit("username", "transactionId2", 50.0);

        List<Transaction> transactions = playerService.getTransactionHistory("username");

        assertEquals(2, transactions.size());
        assertEquals(TransactionType.CREDIT, transactions.get(0).getType());
        assertEquals(TransactionType.DEBIT, transactions.get(1).getType());
    }

    @Test
    @DisplayName("Logout Test")
    public void logoutTest() {
        playerService.logout("username");

        List<Action> actions = playerService.getPlayerActions("username");

        assertEquals(1, actions.size());
        assertEquals("username", actions.get(0).getUsername());
        assertEquals("Выход из системы", actions.get(0).getAction());
        assertEquals("", actions.get(0).getDetail());
    }
}
