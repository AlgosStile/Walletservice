package wallet_service.out.controller;

import wallet_service.in.model.Transaction;
import wallet_service.out.service.PlayerService;

import java.util.List;


public class TransactionController {

    private final PlayerService playerService;


    public TransactionController(PlayerService playerService) {
        this.playerService = playerService;
    }


    public void debitTransaction(String username, String transactionId, double amount) throws Exception {
        try {
            playerService.debit(username, transactionId, amount);
            System.out.println("Дебетовая транзакция прошла успешно");
        } catch (Exception e) {
            System.out.println("Дебетовая транзакция не удалась: " + e.getMessage());
        }
    }


    public void creditTransaction(String username, String transactionId, double amount) throws Exception {
        try {
            playerService.credit(username, transactionId, amount);
            System.out.println("Кредитная транзакция прошла успешно --> $");
        } catch (Exception e) {
            System.out.println("Кредитная транзакция не удалась ¯\\_(ツ)_/¯ : " + e.getMessage());
        }
    }


    public List<Transaction> getTransactionHistory(String username) {
        return playerService.getTransactionHistory(username);
    }

}