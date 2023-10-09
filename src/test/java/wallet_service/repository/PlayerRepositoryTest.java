package wallet_service.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import wallet_service.model.Player;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PlayerRepositoryTest {
    private PlayerRepository playerRepository;

    @Before
    public void setUp() throws Exception {
        playerRepository = new PlayerRepository();
    }

    /**
     * testAddPlayer() и testGetPlayer() Проверяют, что игрок корректно добавляется в репозиторий и возвращается
     * при вызове getPlayer().
     */
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


    /**
     * testGetAllPlayers() Проверяет, что метод getAllPlayers() возвращает всех добавленных в репозиторий игроков.
     */
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

    /**
     * Тест успешного удаления игрока.
     */
    @Test
    public void testRemovePlayer() {
        String username = "test";
        Player player = new Player(username, "1234");
        playerRepository.addPlayer(player);

        playerRepository.removePlayer(username);

        Player retrievedPlayer = playerRepository.getPlayer(username);
        Assert.assertNull(retrievedPlayer);
    }

    /**
     * Проверка удаления игрока, которого нет в репозитории.
     */
    @Test
    public void testRemoveNonExistingPlayer() {
        String nonExistingPlayerName = "notExist";
        playerRepository.removePlayer(nonExistingPlayerName);

        Player retrievedPlayer = playerRepository.getPlayer(nonExistingPlayerName);
        Assert.assertNull(retrievedPlayer);
    }

    /**
     * Проверка удаления игрока при передаче пустого имени.
     */
    @Test
    public void testRemovePlayerWithEmptyName() {
        playerRepository.removePlayer("");

        Player retrievedPlayer = playerRepository.getPlayer("");
        Assert.assertNull(retrievedPlayer);
    }


    @After
    public void tearDown() {
        playerRepository = null;
    }
}
