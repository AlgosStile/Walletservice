package wallet_service.in.service;


import wallet_service.in.model.Action;
import wallet_service.in.model.Player;
import wallet_service.in.model.Transaction;
import wallet_service.in.repository.PlayerRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс PlayerService определяет сервис, который предоставляет функциональность для управления игроками и их транзакциями.
 * Он включает в себя операции регистрации, аутентификации, управления балансом, получения истории транзакций, действий и т.д.
 *
 * @author Олег Тодор
 * @since 1.0.0
 */
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

    PlayerRepository getPlayerRepository();
}
