package main.java.org.wallet_service.out.repository;

import main.java.org.wallet_service.out.model.Transaction;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Класс TransactionRepository представляет репозиторий для управления данными транзакций,
 * связанных с финансовыми операциями игроков.
 *
 * @author Олег Тодор
 */
@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Конструктор класса TransactionRepository.
     *
     * @param jdbcTemplate объект JdbcTemplate для выполнения запросов к базе данных
     */
    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Сохраняет транзакцию в базе данных.
     *
     * @param transaction объект транзакции, который нужно сохранить
     */
    public void saveTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (username, amount, type, balance) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, transaction.getUsername(), transaction.getAmount(), transaction.getType(), transaction.getBalance());
    }

    /**
     * Возвращает список транзакций по указанному имени пользователя.
     *
     * @param username имя пользователя
     * @return список транзакций, связанных с указанным именем пользователя
     */
    public List<Transaction> findByPlayerUsername(String username) {
        String sql = "SELECT * FROM transactions WHERE username = ?";
        return jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(Transaction.class));
    }
}
