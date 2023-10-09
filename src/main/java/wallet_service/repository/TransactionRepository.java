package wallet_service.repository;

import wallet_service.model.Transaction;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;
import java.util.Map;

public class TransactionRepository {
    private Map<String, Transaction> transactions;

    public TransactionRepository() {
        this.transactions = new ConcurrentHashMap<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    public Transaction getTransaction(String id) {
        return transactions.get(id);
    }

    public Collection<Transaction> getAllTransactions() {
        return transactions.values();
    }
}
