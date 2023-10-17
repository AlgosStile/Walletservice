package wallet_service.in.service;


import wallet_service.in.model.Action;
import wallet_service.in.model.Player;
import wallet_service.in.model.Transaction;

import java.sql.SQLException;
import java.util.List;


public interface PlayerService {

    List<Action> getPlayerActions(String username);
    void registerPlayer(String username, String password) throws SQLException;
    boolean authenticatePlayer(String username, String password) throws SQLException;
    boolean isUserRegistered(String username) throws SQLException;
    boolean isUserAuthenticated(String username);
    void debit(String username, int transactionId, double amount) throws Exception;
    void credit(String username, int transactionId, double amount) throws Exception;
    List<Transaction> getTransactionHistory(String username) throws SQLException;

    void logout(String username);

    void addAction(String username, String action, String detail);

    double getBalance(String username) throws SQLException;
}
