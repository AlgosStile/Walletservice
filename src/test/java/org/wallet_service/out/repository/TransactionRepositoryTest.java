package test.java.org.wallet_service.out.repository;

import main.java.org.wallet_service.out.model.Transaction;
import main.java.org.wallet_service.out.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TransactionRepositoryTest {
    private TransactionRepository repository;

    @Mock
    private JdbcTemplate mockJdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        repository = new TransactionRepository(mockJdbcTemplate);
    }

    @Test
    void whenSavingTransaction_thenUpdateIsCalled() {
        Transaction transaction = new Transaction();
        transaction.setUsername("user");
        transaction.setAmount(2000);
        transaction.setType("credit");
        transaction.setBalance(10000);

        repository.saveTransaction(transaction);

        verify(mockJdbcTemplate, times(1)).update(
                "INSERT INTO transactions (username, amount, type, balance) VALUES (?, ?, ?, ?)",
                transaction.getUsername(), transaction.getAmount(), transaction.getType(), transaction.getBalance()
        );
    }

}