package wallet_service.in.repository;

import wallet_service.in.config.DBConnection;
import wallet_service.in.model.Transaction;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;
import java.util.Map;

public class TransactionRepository {
    private Map<String, Transaction> transactions; // Коллекция транзакций

    public TransactionRepository() {
        this.transactions = new ConcurrentHashMap<>();

        try (Connection connection = DBConnection.getInstance().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS transactions (id TEXT PRIMARY KEY, username TEXT, amount NUMERIC, type TEXT)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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