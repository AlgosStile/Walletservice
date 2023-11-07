package wallet_service.out.repository;

import lombok.Getter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wallet_service.out.model.Action;

import java.util.List;

@Getter
@Repository
public class ActionRepository {
    final JdbcTemplate jdbcTemplate;

    public ActionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAction(Action action) {
        String sql = "INSERT INTO actions (username, action, detail) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, action.getUsername(), action.getAction(), action.getDetail());
    }

    public List<Action> findByUsername(String username) {
        String sql = "SELECT * FROM actions WHERE username = ?";
        return jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(Action.class));
    }

    public void deleteAll() {
    }

}
