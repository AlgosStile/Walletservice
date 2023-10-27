package wallet_service.in.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import wallet_service.in.repository.PlayerRepository;
import wallet_service.in.service.PlayerService;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerControllerTest {
    @Mock
    private PlayerService playerService;
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerController playerController;

    @Before
    public void setUp() throws Exception {
        when(playerService.authenticatePlayer(anyString(), anyString())).thenReturn(true);

    }

    @Test
    @DisplayName("Test Player Registration")
    public void testRegisterPlayer() throws SQLException {
        playerController.registerPlayer("testUser", "testPassword");
        verify(playerService, times(1)).registerPlayer("testUser", "testPassword");
    }

    @Test
    @DisplayName("Test Player Authentication")
    public void testAuthenticatePlayer() throws Exception {
        playerController.authenticatePlayer("testUser", "testPassword");
        verify(playerService, times(1)).authenticatePlayer("testUser", "testPassword");
    }
}
