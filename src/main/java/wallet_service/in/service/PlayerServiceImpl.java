package wallet_service.in.service;

import wallet_service.in.controller.TransactionType;
import wallet_service.in.model.Action;
import wallet_service.in.model.Player;
import wallet_service.in.model.Transaction;
import wallet_service.in.repository.PlayerRepository;
import wallet_service.in.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerServiceImpl implements PlayerService {
    private PlayerRepository playerRepository;
    private TransactionRepository transactionRepository;
    private List<Action> actions;
    private String authenticatedUser;

    public PlayerServiceImpl(PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
        this.actions = new CopyOnWriteArrayList<>();
    }

    public synchronized void addAction(String username, String action, String detail) {
        actions.add(new Action(username, action, detail));
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

    public void registerPlayer(String username, String password) {
        Player existingPlayer = playerRepository.getPlayer(username);
        if (existingPlayer != null) {
            throw new RuntimeException("Имя пользователя уже занято!");
        }
        Player player = new Player(username, password);
        playerRepository.addPlayer(player);
        addAction(username, "Регистрация игрока", "");
    }

    public boolean authenticatePlayer(String username, String password) {
        Player player = playerRepository.getPlayer(username);
        boolean result = player != null && player.getPassword().equals(password);
        if (result) {
            authenticatedUser = username;
        }
        addAction(username, "Аутентификация игрока", result ? "Успешно" : "Неудачно");
        return result;
    }

    public boolean isUserRegistered(String username) {
        Player player = playerRepository.getPlayer(username);
        return player != null;
    }

    public boolean isUserAuthenticated(String username) {
        return username.equals(authenticatedUser);
    }

    @Override
    public double getBalance(String username) {
        if (authenticatedUser == null || !authenticatedUser.equals(username)) {
            throw new RuntimeException("Пользователь не аутентифицирован. Введите регистрационные данные.");
        }
        Player player = playerRepository.getPlayer(username);
        return player != null ? player.getBalance() : 0;
    }

    public void debit(String username, String transactionId, double amount) throws Exception {
        Player player = playerRepository.getPlayer(username);
        if (player == null) {
            throw new Exception("Игрок не найден");
        }
        if (player.getBalance() < amount) {
            throw new Exception("Недостаточно средств на счете");
        }
        if (transactionRepository.getTransaction(transactionId) != null) {
            throw new Exception("Транзакция с этим идентификатором уже существует");
        }

        player.debit(transactionId, amount);
        Transaction transaction = new Transaction(transactionId, amount, TransactionType.DEBIT);
        transactionRepository.addTransaction(transaction);
        boolean result = transactionRepository.getTransaction(transactionId) != null;
        addAction(username, "Дебит", result ? "Успешно" : "Неудачно");
    }

    public void credit(String username, String transactionId, double amount) throws Exception {
        Player player = playerRepository.getPlayer(username);
        if (player == null) {
            throw new Exception("Игрок не найден");
        }
        if (transactionRepository.getTransaction(transactionId) != null) {
            throw new Exception("Транзакция с этим идентификатором уже существует");
        }

        player.credit(transactionId, amount);
        Transaction transaction = new Transaction(transactionId, amount, TransactionType.CREDIT);
        transactionRepository.addTransaction(transaction);
        boolean result = transactionRepository.getTransaction(transactionId) != null;
        addAction(username, "Кредит", result ? "Успешно" : "Неудачно");
    }


    @Override
    public List<Transaction> getTransactionHistory(String username) {
        if (authenticatedUser == null || !authenticatedUser.equals(username)) {
            throw new RuntimeException("Пользователь не аутентифицирован. Введите регистрационные данные.");
        }
        Player player = playerRepository.getPlayer(username);
        return player != null ? new ArrayList<>(player.getTransactions()) : new ArrayList<>();
    }


    public void logout(String username) {
        addAction(username, "Выход из системы", "");
        playerRepository.removePlayer(username);
        authenticatedUser = null;
    }

}



