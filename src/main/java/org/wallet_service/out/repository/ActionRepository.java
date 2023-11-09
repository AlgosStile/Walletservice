package main.java.org.wallet_service.out.repository;

import lombok.Getter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import main.java.org.wallet_service.out.model.Action;

import java.util.List;

/**
 * Класс ActionRepository представляет репозиторий для управления действиями,
 * связанными с игровыми действиями пользователя.
 *
 * @author Олег Тодор
 */
@Getter
@Repository
public class ActionRepository {
    final JdbcTemplate jdbcTemplate;

    /**
     * Конструктор класса ActionRepository.
     *
     * @param jdbcTemplate объект JdbcTemplate для выполнения запросов к базе данных
     */
    public ActionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Сохраняет действие в базе данных.
     *
     * @param action объект действия, который нужно сохранить
     */
    public void saveAction(Action action) {
        String sql = "INSERT INTO actions (username, action, detail) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, action.getUsername(), action.getAction(), action.getDetail());
    }

    /**
     * Возвращает список действий, связанных с указанным именем пользователя.
     *
     * @param username имя пользователя
     * @return список действий, связанных с указанным именем пользователя
     */
    public List<Action> findByUsername(String username) {
        String sql = "SELECT * FROM actions WHERE username = ?";
        return jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(Action.class));
    }

    /**
     * Удаляет все действия из базы данных.
     */
    public void deleteAll() {
    }
}
