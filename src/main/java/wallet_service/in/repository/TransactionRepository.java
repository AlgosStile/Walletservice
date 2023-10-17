package wallet_service.in.repository;

import wallet_service.in.config.DBConnection;
import wallet_service.in.controller.TransactionType;
import wallet_service.in.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TransactionRepository {

    private static final String INSERT_SQL = "INSERT INTO wallet.transactions(id, username, amount, type, balance) VALUES (nextval('transaction_id_seq'), ?, ?, ?)";
    private static final String SELECT_SQL = "SELECT * FROM wallet.transactions WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM wallet.transactions WHERE username = ?";

    private Connection connection;

    public TransactionRepository() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
    }

    public void addTransaction(String username, Transaction transaction) throws SQLException {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1, username);
            preparedStatement.setDouble(2, transaction.getAmount());
            preparedStatement.setString(3, transaction.getType().toString());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.commit();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            if (connection != null) {
                try {
                    System.out.println("Транзакция отменена");
                    connection.rollback();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public Transaction getTransaction(int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new Transaction(rs.getInt("id"),
                            rs.getDouble("amount"),
                            TransactionType.valueOf(rs.getString("type")));
                }
            }
        }
        return null;
    }

    public List<Transaction> getAllTransactions(String username) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL)) {
            preparedStatement.setString(1, username);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Transaction ID: " + rs.getString("id") +
                            ", Amount: " + rs.getDouble("amount") +
                            ", Type: " + rs.getString("type"));
                }
            }
        }
        return null;
    }
}