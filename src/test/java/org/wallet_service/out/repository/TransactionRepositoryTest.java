package test.java.org.wallet_service.out.repository;


import main.java.org.wallet_service.out.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import main.java.org.wallet_service.out.model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TransactionRepository.class)
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        clearTransactions();
    }

    @Test
    @DisplayName("Test save transaction")
    public void testSaveTransaction() {
        String username = "username";

        jdbcTemplate.update("INSERT INTO players (username, password, balance) VALUES (?, ?, ?)",
                username, "password", 100);

        Transaction transaction = new Transaction(username, 2.0, "DEBIT", 0);

        transactionRepository.saveTransaction(transaction);

        Transaction savedTransaction = retrieveTransactionByUsername(username);

        assertNotNull(savedTransaction);
        assertEquals(username, savedTransaction.getUsername());
        assertEquals(2.0, savedTransaction.getAmount());
        assertEquals("DEBIT", savedTransaction.getType());
        assertEquals(0, savedTransaction.getBalance());
    }

    private Transaction retrieveTransactionByUsername(String username) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM transactions WHERE username = ?",
                new Object[]{username},
                (resultSet, rowNum) -> new Transaction(
                        resultSet.getString("username"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("type"),
                        resultSet.getInt("balance")
                )
        );
    }

    private void clearTransactions() {
        jdbcTemplate.update("DELETE FROM transactions");
    }
}
