package wallet_service.repository;

import org.junit.Before;
import org.junit.Test;
import wallet_service.model.Player;

import java.util.Collection;

import static org.junit.Assert.*;

public class PlayerRepositoryTest {

    @Before
    public void setUp() throws Exception {
    }

    //testAddPlayer() и testGetPlayer() Проверяют, что игрок корректно добавляется в репозиторий и возвращается
    // обратно при вызове getPlayer().
    @Test
    public void testAddPlayer() {
        PlayerRepository playerRepository = new PlayerRepository();
        Player player = new Player("username", "password");

        playerRepository.addPlayer(player);

        assertEquals(player, playerRepository.getPlayer("username"));
    }


    @Test
    public void testGetPlayer() {
        PlayerRepository playerRepository = new PlayerRepository();
        Player player = new Player("username", "password");
        playerRepository.addPlayer(player);

        Player retrievedPlayer = playerRepository.getPlayer("username");

        assertEquals(player, retrievedPlayer);
    }


    //testGetAllPlayers() Проверяет, что метод getAllPlayers() возвращает всех добавленных в репозиторий игроков.
    @Test
    public void testGetAllPlayers() {
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
}