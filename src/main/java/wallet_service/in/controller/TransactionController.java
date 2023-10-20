package wallet_service.in.controller;

import wallet_service.in.config.DBConnection;
import wallet_service.in.model.Transaction;
import wallet_service.in.repository.PlayerRepository;
import wallet_service.in.repository.TransactionRepository;
import wallet_service.in.service.PlayerService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class TransactionController {
    private PlayerService playerService;
    private PlayerRepository playerRepository;
    private TransactionRepository transactionRepository;
    private Connection connection;

    public TransactionController(PlayerService playerService, PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerService = playerService;
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
        this.connection = DBConnection.getInstance().getConnection();
    }

    public void debitTransaction(String username, int id, double amount) throws Exception {
        try {
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }
            playerService.debit(username, id, amount);
            System.out.println("Дебетовая транзакция прошла успешно --> $");
            connection.commit(); //коммитим дебет транз тут
        } catch (Exception exception) {
            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException rollbackExcep) {
                    System.out.println("Couldn't roll back transaction: " + rollbackExcep.getMessage());
                }
            }
            throw exception;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }


    public void creditTransaction(String username, int id, double amount) throws Exception {
        try {
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }
            playerService.credit(username, id, amount);
            System.out.println("Кредитная транзакция прошла успешно --> $");
            connection.commit(); // Коммитим транзакцию кредита
        } catch (Exception exception) {
            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException rollbackExcep) {
                    System.out.println("Couldn't roll back transaction: " + rollbackExcep.getMessage());
                }
            }
            String message = "Кредитная транзакция завершилась неудачно. Возникла ошибка: " + exception.getMessage();
            System.err.println(message);
            throw exception;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Включаем обратно режим автоподтверждения
                } catch (SQLException setAutoCommitExcep) {
                    System.out.println("Couldn't set auto commit: " + setAutoCommitExcep.getMessage());
                }
            }
        }
    }


    public List<Transaction> getTransactionHistory(String username) throws SQLException {
        return playerService.getTransactionHistory(username);
    }

}