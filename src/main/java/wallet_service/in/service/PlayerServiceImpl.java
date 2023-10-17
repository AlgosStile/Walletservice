package wallet_service.in.service;

import wallet_service.in.config.DBConnection;
import wallet_service.in.controller.TransactionType;
import wallet_service.in.model.Action;
import wallet_service.in.model.Player;
import wallet_service.in.model.Transaction;
import wallet_service.in.repository.PlayerRepository;
import wallet_service.in.repository.TransactionRepository;
import wallet_service.in.repository.ActionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class PlayerServiceImpl implements PlayerService {
    private PlayerRepository playerRepository;
    private TransactionRepository transactionRepository;
    private List<Action> actions;
    private String authenticatedUser;
    private ActionRepository actionRepository;

    public PlayerServiceImpl() throws SQLException {
        this.playerRepository = new PlayerRepository();
        this.transactionRepository = new TransactionRepository(this.playerRepository);
        this.actions = new ArrayList<>();
        this.actionRepository = new ActionRepository();
    }

    public PlayerServiceImpl(PlayerRepository playerRepository, TransactionRepository transactionRepository) throws SQLException {
        if (playerRepository == null || transactionRepository == null) {
            throw new IllegalArgumentException("PlayerRepository и TransactionRepository не могут быть null");
        }
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
        this.actions = new ArrayList<>();
        this.actionRepository = new ActionRepository();
    }



    @Override
    public synchronized void addAction(String username, String action, String detail) {
        Action actionObject = new Action(username, action, detail);

        actions.add(actionObject);

        try {
            actionRepository.addAction(actionObject); // Записываем действия в БД
        }
        catch(SQLException e) {
            // Обработка исключения
            e.printStackTrace();
        }
    }



    public List<Action> getPlayerActions(String username) {
        List<Action> playerActions = new ArrayList<>();
        for (Action action : actions) {
            if (action.getUsername().equals(username)) {
                playerActions.add(action);
            }
        }
        return playerActions;
    }

    public void registerPlayer(String username, String password) throws SQLException {
        Player existingPlayer = playerRepository.getPlayer(username);
        if (existingPlayer != null) {
            throw new RuntimeException("Имя пользователя уже занято!");
        }
        Player player = new Player(username, password);
        playerRepository.addPlayer(player);
        addAction(username, "Регистрация игрока", "");
    }

    public boolean authenticatePlayer(String username, String password) throws SQLException {
        Player player = playerRepository.getPlayer(username);
        boolean result = player != null && player.getPassword().equals(password);
        if (result) {
            authenticatedUser = username;
        }
        addAction(username, "Аутентификация игрока", result ? "Успешно" : "Неудачно");
        return result;
    }

    public boolean isUserRegistered(String username) throws SQLException {
        Player player = playerRepository.getPlayer(username);
        return player != null;
    }

    public boolean isUserAuthenticated(String username) {
        return username.equals(authenticatedUser);
    }

    @Override
    public double getBalance(String username) throws SQLException {
        Player player = playerRepository.getPlayer(username);
        if (player != null) {
            return player.getBalance();
        } else {
            throw new RuntimeException("Пользователь не аутентифицирован: " + username);
        }
    }


    public void debit(String username, int transactionId, double amount) throws Exception {
        Player player = playerRepository.getPlayer(username);
        if (player == null) {
            throw new Exception("Игрок не найден");
        }
        if (player.getBalance() < amount) {
            throw new Exception("Недостаточно средств на счете");
        }
        player.debit(transactionId, amount);
        playerRepository.updatePlayer(username, player.getBalance());
        Transaction transaction = new Transaction(transactionId, amount, TransactionType.DEBIT);
        transactionRepository.addTransaction(username, transaction);


        boolean result = transactionRepository.getTransaction(transactionId) != null;
        addAction(username, "Дебит", result ? "Успешно" : "Неудачно");

        DBConnection.getInstance().getConnection().commit();

    }

    public void credit(String username, int transactionId, double amount) throws Exception {
        Player player = playerRepository.getPlayer(username);
        if (player == null) {
            throw new Exception("Игрок не найден");
        }

        player.credit(transactionId, amount);
        playerRepository.updatePlayer(username, player.getBalance());

        Transaction transaction = new Transaction(transactionId, amount, TransactionType.CREDIT);
        transactionRepository.addTransaction(username, transaction);

        boolean result = transactionRepository.getTransaction(transactionId) != null;
        addAction(username, "Кредит", result ? "Успешно" : "Неудачно");
        DBConnection.getInstance().getConnection().commit();
    }


    @Override
    public List<Transaction> getTransactionHistory(String username) throws SQLException {
        if (authenticatedUser == null || !authenticatedUser.equals(username)) {
            throw new RuntimeException("Пользователь не аутентифицирован. Введите регистрационные данные.");
        }
        return transactionRepository.getAllTransactions(username);
    }


    public void logout(String username) {
        addAction(username, "Выход из системы", "");
        try {
            playerRepository.removePlayer(username);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        authenticatedUser = null;
    }


}