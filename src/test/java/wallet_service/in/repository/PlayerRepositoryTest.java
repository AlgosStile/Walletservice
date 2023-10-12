package wallet_service.in.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wallet_service.in.model.Player;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerRepositoryTest {
    private PlayerRepository playerRepository;

    @BeforeEach
    public void setUp() throws Exception {
        playerRepository = new PlayerRepository();
    }

    @Test
    @DisplayName("Add Player Test")
    public void AddPlayerTest() {
        PlayerRepository playerRepository = new PlayerRepository();
        Player player = new Player("username", "password");

        playerRepository.addPlayer(player);

        assertEquals(player, playerRepository.getPlayer("username"));
    }

    @Test
    @DisplayName("Get Player Test")
    public void GetPlayerTest() {
        PlayerRepository playerRepository = new PlayerRepository();
        Player player = new Player("username", "password");
        playerRepository.addPlayer(player);

        Player retrievedPlayer = playerRepository.getPlayer("username");

        assertEquals(player, retrievedPlayer);
    }

    @Test
    @DisplayName("Get All Players Test")
    public void GetAllPlayersTest() {
        PlayerRepository playerRepository = new PlayerRepository();
        Player player1 = new Player("username1", "password1");
        Player player2 = new Player("username2", "password2");
        playerRepository.addPlayer(player1);
        playerRepository.addPlayer(player2);

        Collection<Player> allPlayers = playerRepository.getAllPlayers();

        assertEquals(2, allPlayers.size());
        assertTrue(allPlayers.contains(player1));
        assertTrue(allPlayers.contains(player2));
    }

    @Test
    @DisplayName("Remove Player Test")
    public void RemovePlayerTest() {
        String username = "test";
        Player player = new Player(username, "1234");
        playerRepository.addPlayer(player);

        playerRepository.removePlayer(username);

        Player retrievedPlayer = playerRepository.getPlayer(username);
        assertNull(retrievedPlayer);
    }

    @Test
    @DisplayName("Remove Non-Existing Player Test")
    public void RemoveNonExistingPlayerTest() {
        String nonExistingPlayerName = "notExist";
        playerRepository.removePlayer(nonExistingPlayerName);

        Player retrievedPlayer = playerRepository.getPlayer(nonExistingPlayerName);
        assertNull(retrievedPlayer);
    }

    @Test
    @DisplayName("Remove Player With Empty Name Test")
    public void RemovePlayerWithEmptyNameTest() {
        playerRepository.removePlayer("");

        Player retrievedPlayer = playerRepository.getPlayer("");
        assertNull(retrievedPlayer);
    }
}
