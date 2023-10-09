package wallet_service.repository;

import org.junit.Before;
import org.junit.Test;
import wallet_service.controller.TransactionType;
import wallet_service.model.Transaction;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionRepositoryTest {

    @Before
    public void setUp() throws Exception {
    }


    //testAddTransaction() и testGetTransaction() Проверяют, что транзакция корректно добавляется в репозиторий
    // и возвращается при вызове getTransaction().
    @Test
    public void testAddTransaction() {
        TransactionRepository transactionRepository = new TransactionRepository();
        Transaction transaction = new Transaction("id", 100.0, TransactionType.DEBIT);

        transactionRepository.addTransaction(transaction);

        assertEquals(transaction, transactionRepository.getTransaction("id"));
    }

    @Test
    public void testGetTransaction() {
        TransactionRepository transactionRepository = new TransactionRepository();
        Transaction transaction = new Transaction("id", 100.0, TransactionType.DEBIT);
        transactionRepository.addTransaction(transaction);

        Transaction retrievedTransaction = transactionRepository.getTransaction("id");

        assertEquals(transaction, retrievedTransaction);
    }


    //testGetAllTransactions() Проверяет, что метод getAllTransactions() возвращает все добавленные в репозиторий
    // транзакции.
    @Test
    public void testGetAllTransactions() {
        TransactionRepository transactionRepository = new TransactionRepository();
        Transaction transaction1 = new Transaction("id1", 100.0, TransactionType.DEBIT);
        Transaction transaction2 = new Transaction("id2", 200.0, TransactionType.CREDIT);
        transactionRepository.addTransaction(transaction1);
        transactionRepository.addTransaction(transaction2);

        Collection<Transaction> allTransactions = transactionRepository.getAllTransactions();

        assertEquals(2, allTransactions.size());
        assertTrue(allTransactions.contains(transaction1));
        assertTrue(allTransactions.contains(transaction2));
    }
}