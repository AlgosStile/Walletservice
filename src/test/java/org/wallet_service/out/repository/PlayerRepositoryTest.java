package test.java.org.wallet_service.out.repository;


import main.java.org.wallet_service.out.model.Player;
import main.java.org.wallet_service.out.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlayerRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        playerRepository = new PlayerRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("Find player by username")
    void findByUsername() {
        String username = "player1";
        playerRepository.findByUsername(username);

        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(), any(BeanPropertyRowMapper.class));
    }

    @Test
    @DisplayName("Save player")
    void savePlayer() {
        Player player = new Player();
        player.setUsername("player1");
        player.setPassword("password");
        playerRepository.savePlayer(player);

        ArgumentCaptor<Object[]> argumentCaptor = ArgumentCaptor.forClass(Object[].class);
        verify(jdbcTemplate, times(1)).update(anyString(), argumentCaptor.capture());
        Object[] capturedArgs = argumentCaptor.getValue();

        assertEquals(player.getUsername(), capturedArgs[0]);
        assertEquals(player.getPassword(), capturedArgs[1]);
        assertEquals(player.getBalance(), capturedArgs[2]);
    }

    @Test
    @DisplayName("Update balance by username")
    void updateBalanceByUsername() {
        String username = "player1";
        BigDecimal balance = BigDecimal.TEN;

        playerRepository.updateBalanceByUsername(username, balance);

        ArgumentCaptor<Object[]> argumentCaptor = ArgumentCaptor.forClass(Object[].class);
        verify(jdbcTemplate, times(1)).update(anyString(), argumentCaptor.capture());
        Object[] capturedArgs = argumentCaptor.getValue();

        assertEquals(balance, capturedArgs[0]);
        assertEquals(username, capturedArgs[1]);
    }
}
