package wallet_service.in.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wallet_service.in.service.PlayerService;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class PlayerControllerTest {

    private PlayerService playerService;
    private PlayerController playerController;

    @BeforeEach
    public void setUp() throws Exception {
        playerService = mock(PlayerService.class);
        playerController = new PlayerController(playerService);
    }

    @Test
    @DisplayName("Register Player Test")
    public void registerPlayerTest() throws SQLException {
        String username = "testUser";
        String password = "testPassword";

        playerController.registerPlayer(username, password);

        verify(playerService).registerPlayer(username, password);
    }

    @Test
    @DisplayName("Authenticate Player Test")
    public void authenticatePlayerTest() throws SQLException {
        String username = "testUser";
        String password = "testPassword";

        when(playerService.authenticatePlayer(username, password)).thenReturn(true);

        playerController.authenticatePlayer(username, password);

        verify(playerService).authenticatePlayer(username, password);
    }

    @Test
    @DisplayName("Get Balance Test")
    public void getBalanceTest() throws SQLException {
        String username = "testUser";
        double balance = 100.0;

        when(playerService.isUserRegistered(username)).thenReturn(true);
        when(playerService.isUserAuthenticated(username)).thenReturn(true);
        when(playerService.getBalance(username)).thenReturn(balance);

        playerController.getBalance(username);

        verify(playerService).isUserRegistered(username);
        verify(playerService).isUserAuthenticated(username);
        verify(playerService).getBalance(username);
    }

    @Test
    @DisplayName("Logout Player Test")
    public void logoutPlayerTest() {
        String username = "testUser";

        playerController.logoutPlayer(username);

        verify(playerService).logout(username);
    }

    @Test
    @DisplayName("Get Player Actions Test")
    public void getPlayerActionsTest() {
        String username = "testUser";

        playerController.getPlayerActions(username);

        verify(playerService).getPlayerActions(username);
    }
}
