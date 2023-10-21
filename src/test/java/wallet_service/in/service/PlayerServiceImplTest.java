package wallet_service.in.service;


import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import wallet_service.in.model.Player;
import wallet_service.in.repository.PlayerRepository;

import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceImplTest {

    @Mock
    PlayerRepository playerRepository;
    @InjectMocks
    PlayerServiceImpl playerService;


    @Test
    @DisplayName("Test if user is registered")
    public void isUserRegisteredTest() throws SQLException {
        when(playerRepository.getPlayer("User1")).thenReturn(new Player("User1", "Password1"));
        when(playerRepository.getPlayer("NonExistentUser")).thenReturn(null);

        assertTrue(playerService.isUserRegistered("User1"));
        assertFalse(playerService.isUserRegistered("NonExistentUser"));
    }
}
