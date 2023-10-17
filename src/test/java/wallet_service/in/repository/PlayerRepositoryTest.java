package wallet_service.in.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wallet_service.in.model.Player;

import java.sql.SQLException;
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
    public void AddPlayerTest() throws SQLException {
        PlayerRepository playerRepository = new PlayerRepository();
        Player player = new Player("username", "password");

        playerRepository.addPlayer(player);

        assertEquals(player, playerRepository.getPlayer("username"));
    }

    @Test
    @DisplayName("Get Player Test")
    public void GetPlayerTest() throws SQLException {
        PlayerRepository playerRepository = new PlayerRepository();
        Player player = new Player("username", "password");
        playerRepository.addPlayer(player);

        Player retrievedPlayer = playerRepository.getPlayer("username");

        assertEquals(player, retrievedPlayer);
    }



    @Test
    @DisplayName("Remove Player Test")
    public void RemovePlayerTest() throws SQLException {
        String username = "test";
        Player player = new Player(username, "1234");
        playerRepository.addPlayer(player);

        playerRepository.removePlayer(username);

        Player retrievedPlayer = playerRepository.getPlayer(username);
        assertNull(retrievedPlayer);
    }

    @Test
    @DisplayName("Remove Non-Existing Player Test")
    public void RemoveNonExistingPlayerTest() throws SQLException {
        String nonExistingPlayerName = "notExist";
        playerRepository.removePlayer(nonExistingPlayerName);

        Player retrievedPlayer = playerRepository.getPlayer(nonExistingPlayerName);
        assertNull(retrievedPlayer);
    }

    @Test
    @DisplayName("Remove Player With Empty Name Test")
    public void RemovePlayerWithEmptyNameTest() throws SQLException {
        playerRepository.removePlayer("");

        Player retrievedPlayer = playerRepository.getPlayer("");
        assertNull(retrievedPlayer);
    }
}
