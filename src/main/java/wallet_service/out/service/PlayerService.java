package wallet_service.out.service;


import wallet_service.out.model.Action;
import wallet_service.out.model.Transaction;

import java.util.List;

public interface PlayerService {
    void addAction(String username, String action, String detail);
    List<Action> getPlayerActions(String username);
    void registerPlayer(String username, String password);
    boolean authenticatePlayer(String username, String password);
    double getBalance(String username);
    boolean isUserRegistered(String username);
    boolean isUserAuthenticated(String username);
    void debit(String username, String transactionId, double amount) throws Exception;
    void credit(String username, String transactionId, double amount) throws Exception;
    List<Transaction> getTransactionHistory(String username);

    void logout(String username);
}
