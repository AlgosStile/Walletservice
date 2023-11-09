package main.java.org.wallet_service.out.repository;

import main.java.org.wallet_service.out.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Класс PlayerRepository представляет репозиторий для управления данными игроков,
 * связанных с игровыми данными пользователя.
 *
 * @author Олег Тодор
 */
@Repository
public class PlayerRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Конструктор класса PlayerRepository.
     *
     * @param jdbcTemplate объект JdbcTemplate для выполнения запросов к базе данных
     */
    @Autowired
    public PlayerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Возвращает игрока по указанному имени пользователя.
     *
     * @param username имя пользователя
     * @return объект игрока, соответствующий указанному имени пользователя
     */
    public Player findByUsername(String username) {
        String sql = "SELECT * FROM players WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper<>(Player.class));
    }

    /**
     * Сохраняет игрока в базе данных.
     *
     * @param player объект игрока, который нужно сохранить
     */
    public void savePlayer(Player player) {
        String sql = "INSERT INTO players (username, password, balance) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, player.getUsername(), player.getPassword(), player.getBalance());
    }

    /**
     * Обновляет баланс игрока по указанному имени пользователя.
     *
     * @param username имя пользователя
     * @param balance  новый баланс игрока
     */
    public void updateBalanceByUsername(String username, BigDecimal balance) {
        String sql = "UPDATE players SET balance = ? WHERE username = ?";
        jdbcTemplate.update(sql, balance, username);
    }

}
