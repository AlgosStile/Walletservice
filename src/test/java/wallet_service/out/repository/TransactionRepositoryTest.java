package wallet_service.out.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wallet_service.out.model.Transaction;
import wallet_service.out.repository.TransactionRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testSaveTransaction() {
        Transaction transaction = new Transaction(100.0, "type", 500);
        transactionRepository.saveTransaction(transaction);
        List<Transaction> transactions = transactionRepository.findByPlayerUsername("username");
        assertEquals(1, transactions.size());
        assertEquals(100.0, transactions.get(0).getAmount());
        assertEquals("type", transactions.get(0).getType());
        assertEquals(500, transactions.get(0).getBalance());
    }
}