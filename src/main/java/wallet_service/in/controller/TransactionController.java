package wallet_service.in.controller;

import wallet_service.in.model.Transaction;
import wallet_service.in.repository.PlayerRepository;
import wallet_service.in.repository.TransactionRepository;
import wallet_service.in.service.PlayerService;

import java.sql.SQLException;
import java.util.List;



public class TransactionController {
    private PlayerService playerService;
    private PlayerRepository playerRepository;
    private TransactionRepository transactionRepository;

    public TransactionController(PlayerService playerService, PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerService = playerService;
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
    }


    public void debitTransaction(String username, int id, double amount) throws Exception {
        try {
            playerService.debit(username, id, amount);
            System.out.println("Дебетовая транзакция прошла успешно");
        } catch (Exception e) {
            System.out.println("Дебетовая транзакция не удалась ¯\\\\_(ツ)_/¯ : " + e.getMessage());
        }
    }


    public void creditTransaction(String username, int id, double amount) throws Exception {
        try {
            playerService.credit(username, id, amount);
            System.out.println("Кредитная транзакция прошла успешно --> $");
        }
            catch (Exception exception) {
                String message = "Кредитная транзакция " + id + " не удалась ¯\\\\_(ツ)_/¯ : " + exception.getMessage();
                System.out.println(message);
                throw new Exception(message);
            }
    }


    public List<Transaction> getTransactionHistory(String username) throws SQLException {
        return playerService.getTransactionHistory(username);
    }

}