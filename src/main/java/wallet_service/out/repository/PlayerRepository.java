package wallet_service.out.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wallet_service.out.model.Player;

import java.math.BigDecimal;

@Repository
public class PlayerRepository {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PlayerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Player findByUsername(String username) {
        String sql = "SELECT * FROM players WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper<>(Player.class));
    }

    public void savePlayer(Player player) {
        String sql = "INSERT INTO players (username, password, balance) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, player.getUsername(), player.getPassword(), player.getBalance());
    }

    public void updateBalanceByUsername(String username, BigDecimal balance) {
        String sql = "UPDATE players SET balance = ? WHERE username = ?";
        jdbcTemplate.update(sql, balance, username);
    }
}