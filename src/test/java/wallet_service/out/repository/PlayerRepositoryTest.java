package wallet_service.out.repository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wallet_service.out.model.Player;
import wallet_service.out.repository.PlayerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    @DisplayName("Test save player")
    public void testFindByUsername() {
        Player player = new Player("username", "password", 100);
        playerRepository.savePlayer(player);
        Optional<Player> optionalPlayer = Optional.ofNullable(playerRepository.findByUsername("username"));
        assertTrue(optionalPlayer.isPresent());
        assertEquals("username", optionalPlayer.get().getUsername());
        assertEquals("password", optionalPlayer.get().getPassword());
        assertEquals(100, optionalPlayer.get().getBalance());
    }
}