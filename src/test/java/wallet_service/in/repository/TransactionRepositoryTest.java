package wallet_service.in.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wallet_service.in.controller.TransactionType;
import wallet_service.in.model.Transaction;

import java.sql.SQLException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionRepositoryTest {

    @BeforeEach
    public void setUp() throws Exception {
    }

//    @Test
//    @DisplayName("Add Transaction Test")
//    public void AddTransactionTest() throws SQLException {
//        TransactionRepository transactionRepository = new TransactionRepository();
//        Transaction transaction = new Transaction("id", 100.0, TransactionType.DEBIT);
//        transactionRepository.addTransaction(transaction);
//    }

//    @Test
//    @DisplayName("Get Transaction Test")
//    public void GetTransactionTest() throws SQLException {
//        TransactionRepository transactionRepository = new TransactionRepository();
//        Transaction transaction = new Transaction("id", 100.0, TransactionType.DEBIT);
//        transactionRepository.addTransaction(transaction);
//
//        Transaction retrievedTransaction = transactionRepository.getTransaction("id");
//
//        assertEquals(transaction, retrievedTransaction);
//    }

//    @Test
//    @DisplayName("Get All Transactions Test")
//    public void GetAllTransactionsTest() throws SQLException {
//        TransactionRepository transactionRepository = new TransactionRepository();
//        Transaction transaction1 = new Transaction("id1", 100.0, TransactionType.DEBIT);
//        Transaction transaction2 = new Transaction("id2", 200.0, TransactionType.CREDIT);
//        transactionRepository.addTransaction(transaction1);
//        transactionRepository.addTransaction(transaction2);
//
//        Collection<Transaction> allTransactions = transactionRepository.getAllTransactions();
//
//        assertEquals(2, allTransactions.size());
//        assertTrue(allTransactions.contains(transaction1));
//        assertTrue(allTransactions.contains(transaction2));
//    }
}
