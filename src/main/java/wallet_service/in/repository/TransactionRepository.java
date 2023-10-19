package wallet_service.in.repository;

import wallet_service.in.config.DBConnection;
import wallet_service.in.controller.TransactionType;
import wallet_service.in.model.Player;
import wallet_service.in.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private static final String INSERT_SQL = "INSERT INTO wallet.transactions(username, amount, type, balance) VALUES (?, ?, ?, ?)";
    private static final String SELECT_SQL = "SELECT * FROM wallet.transactions WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM wallet.transactions WHERE username = ?";

    private Connection connection;
    private PlayerRepository playerRepository;
    private static TransactionRepository instance;

    public TransactionRepository() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
        this.connection = DBConnection.getInstance().getConnection();
    }


    public TransactionRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        this.connection = DBConnection.getInstance().getConnection();
    }

    public static synchronized TransactionRepository getInstance(PlayerRepository playerRepository) throws SQLException {
        if (instance == null) {
            instance = new TransactionRepository(playerRepository);
        }
        return instance;
    }


    public void addTransaction(String username, Transaction transaction) throws SQLException {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1, username);
            preparedStatement.setDouble(2, transaction.getAmount());
            preparedStatement.setString(3, transaction.getType().toString());
            preparedStatement.setDouble(4, playerRepository.getPlayer(username).getBalance());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            Player player = playerRepository.getPlayer(username);
            double newBalance = transaction.getType() == TransactionType.DEBIT
                    ? player.getBalance() - transaction.getAmount()
                    : player.getBalance() + transaction.getAmount();
            playerRepository.updatePlayer(username, newBalance);

            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            if (connection !=null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                } catch(SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }

// <<<<<<<<<<<<Для привязки транзакции по ID>>>>>>>>>>>>>

//    public Transaction getTransaction(int id) throws SQLException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {
//            preparedStatement.setInt(1, id);
//            try (ResultSet rs = preparedStatement.executeQuery()) {
//                if (rs.next()) {
//                    return new Transaction(rs.getInt("id"),
//                            rs.getDouble("amount"),
//                            TransactionType.valueOf(rs.getString("type")));
//                }
//            }
//        }
//        return null;
//    }

    public List<Transaction> getAllTransactions(String username) throws SQLException {
        List<Transaction> transactionList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL)) {
            preparedStatement.setString(1, username);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Transaction ID: " + rs.getString("id") +
                            ", Amount: " + rs.getDouble("amount") +
                            ", Type: " + rs.getString("type"));
                    Transaction transaction = new Transaction(
                            rs.getInt("id"),
                            rs.getDouble("amount"),
                            TransactionType.valueOf(rs.getString("type")));
                    transactionList.add(transaction);
                }
            }
        }
        return transactionList;
    }

}