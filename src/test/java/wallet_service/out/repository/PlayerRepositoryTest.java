package wallet_service.out.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import wallet_service.out.model.Player;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PlayerRepository.class)
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        clearPlayers();
    }

    @Test
    public void testFindByUsername() {
        String username = "username";
        Player player = new Player(username, "password", BigDecimal.ZERO);
        jdbcTemplate.update("INSERT INTO players (username, password, balance) VALUES (?, ?, ?)", username, "password", BigDecimal.ZERO);

        Player foundPlayer = playerRepository.findByUsername(username);

        assertNotNull(foundPlayer);
        assertEquals(username, foundPlayer.getUsername());
        assertEquals("password", foundPlayer.getPassword());
        assertEquals(BigDecimal.ZERO, new BigDecimal(foundPlayer.getBalance()));
    }

    @Test
    public void testSavePlayer() {
        String username = "username";
        Player player = new Player(username, "password", 0); // изменено на 0

        playerRepository.savePlayer(player);

        Player savedPlayer = playerRepository.findByUsername(username);

        assertNotNull(savedPlayer);
        assertEquals(username, savedPlayer.getUsername());
        assertEquals("password", savedPlayer.getPassword());
        assertEquals(0, savedPlayer.getBalance()); // изменено на 0
    }

    @Test
    public void testUpdateBalanceByUsername() {
        String username = "username";
        Player player = new Player(username, "password", BigDecimal.ZERO);
        jdbcTemplate.update("INSERT INTO players (username, password, balance) VALUES (?, ?, ?)", username, "password", BigDecimal.ZERO);

        playerRepository.updateBalanceByUsername(username, BigDecimal.TEN);

        Player updatedPlayer = playerRepository.findByUsername(username);

        assertNotNull(updatedPlayer);
        assertEquals(username, updatedPlayer.getUsername());
        assertEquals("password", updatedPlayer.getPassword());
        assertEquals(BigDecimal.TEN, new BigDecimal(updatedPlayer.getBalance()));
    }

    private void clearPlayers() {
        jdbcTemplate.update("DELETE FROM players");
    }
}