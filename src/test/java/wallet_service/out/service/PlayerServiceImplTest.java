package test.java.wallet_service.out.service;

import main.java.wallet_service.out.service.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import main.java.wallet_service.out.model.Action;
import main.java.wallet_service.out.model.Player;
import main.java.wallet_service.out.model.Transaction;
import main.java.wallet_service.out.repository.ActionRepository;
import main.java.wallet_service.out.repository.PlayerRepository;
import main.java.wallet_service.out.repository.TransactionRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.when;

public class PlayerServiceImplTest {
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private ActionRepository actionRepository;
    @Mock
    private TransactionRepository transactionRepository;
    private PlayerServiceImpl playerService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerServiceImpl(playerRepository, actionRepository, transactionRepository);
    }

    @Test
    @DisplayName("Test get player")
    public void testGetPlayer() {
        Player player = new Player("username", "password", 100);
        when(playerRepository.findByUsername("username")).thenReturn(player);

        Player result = playerService.getPlayer("username");

        assertEquals("username", result.getUsername());
        assertEquals("password", result.getPassword());
        assertEquals(100, result.getBalance());
        verify(playerRepository, times(1)).findByUsername("username");
    }

    @Test
    @DisplayName("Test save player")
    public void testSavePlayer() {
        Player player = new Player("username", "password", 100);
        playerService.savePlayer(player);
        verify(playerRepository, times(1)).savePlayer(player);
    }

    @Test
    @DisplayName("Test update balance")
    public void testDebitTransaction() {
        Player player = new Player("username", "password", 100);
        when(playerRepository.findByUsername("username")).thenReturn(player);

        BigDecimal amount = BigDecimal.TEN;
        playerService.debitTransaction("username", amount);

        assertEquals(90, player.getBalance());
        verify(playerRepository, times(1)).savePlayer(player);
    }

    @Test
    @DisplayName("Test credit transaction")
    public void testCreditTransaction() {
        Player player = new Player("username", "password", 100);
        when(playerRepository.findByUsername("username")).thenReturn(player);

        BigDecimal amount = BigDecimal.TEN;
        playerService.creditTransaction("username", amount);

        assertEquals(110, player.getBalance());
        verify(playerRepository, times(1)).savePlayer(player);
    }

    @Test
    @DisplayName("Test get player actions")
    public void testGetPlayerActions() {
        Action action = new Action(1, "username", "action", "detail");
        when(actionRepository.findByUsername("username")).thenReturn(Arrays.asList(action));

        List<Action> result = playerService.getPlayerActions("username");

        assertEquals(1, result.size());
        assertEquals("username", result.get(0).getUsername());
        assertEquals("action", result.get(0).getAction());
        assertEquals("detail", result.get(0).getDetail());
        verify(actionRepository, times(1)).findByUsername("username");
    }

    @Test
    @DisplayName("Test get transaction history")
    public void testGetTransactionHistory() {
        Transaction transaction = new Transaction("username", 100.0, "type", 500);
        when(transactionRepository.findByPlayerUsername("username")).thenReturn(Arrays.asList(transaction));

        List<Transaction> result = playerService.getTransactionHistory("username");

        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getAmount());
        assertEquals("type", result.get(0).getType());
        assertEquals(500, result.get(0).getBalance());
        verify(transactionRepository, times(1)).findByPlayerUsername("username");
    }
}