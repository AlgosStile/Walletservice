package wallet_service.out.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wallet_service.out.model.Transaction;

import java.util.List;

@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (username, amount, type, balance) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, transaction.getUsername(), transaction.getAmount(), transaction.getType(), transaction.getBalance());
    }

    public List<Transaction> findByPlayerUsername(String username) {
        String sql = "SELECT * FROM transactions WHERE username = ?";
        return jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(Transaction.class));
    }


}
