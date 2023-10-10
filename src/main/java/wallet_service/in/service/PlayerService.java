package wallet_service.in.service;

import wallet_service.in.controller.TransactionType;
import wallet_service.in.model.Action;
import wallet_service.in.model.Player;
import wallet_service.in.model.Transaction;
import wallet_service.out.repository.PlayerRepository;
import wallet_service.out.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Сервис, обеспечивающий взаимодействие между игроками и кошельками в игровом приложении.
 */
public class PlayerService {
    private PlayerRepository playerRepository;
    private TransactionRepository transactionRepository;

    private List<Action> actions;

    /**
     * Конструктор класса.
     *
     * @param playerRepository      Репозиторий игроков.
     * @param transactionRepository Репозиторий транзакций.
     */
    public PlayerService(PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
        this.actions = new CopyOnWriteArrayList<>();
    }

    /**
     * Добавить действие для заданного пользователя.
     *
     * @param username Имя пользователя.
     * @param action   Действие.
     * @param detail   Детали действия.
     */
    public synchronized void addAction(String username, String action, String detail) {
        actions.add(new Action(username, action, detail));
    }


    /**
     * Извлечь все действия заданного пользователя.
     *
     * @param username Имя пользователя.
     * @return Список действий пользователя.
     */
    public List<Action> getPlayerActions(String username) {
        List<Action> playerActions = new ArrayList<>();
        for (Action action : actions) {
            if (action.getUsername().equals(username)) {
                playerActions.add(action);
            }
        }
        return playerActions;
    }

    /**
     * Зарегистрировать нового пользователя.
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @throws RuntimeException если имя пользователя уже занято.
     */
    public void registerPlayer(String username, String password) {
        Player existingPlayer = playerRepository.getPlayer(username);
        if (existingPlayer != null) {
            throw new RuntimeException("Имя пользователя уже занято!");
        }
        Player player = new Player(username, password);
        playerRepository.addPlayer(player);
        addAction(username, "Регистрация игрока", "");
    }

    /**
     * Аутентифицировать пользователя.
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @return true если аутентификация прошла успешно, false в противном случае.
     */
    public boolean authenticatePlayer(String username, String password) {
        Player player = playerRepository.getPlayer(username);
        boolean result = player != null && player.getPassword().equals(password);
        addAction(username, "Аутентификация игрока", result ? "Успешно" : "Неудачно");
        return result;
    }

    /**
     * Получить баланс пользователя.
     *
     * @param username Имя пользователя.
     * @return Баланс пользователя если пользователь найден, 0 в противном случае.
     */
    public double getBalance(String username) {
        Player player = playerRepository.getPlayer(username);
        return player != null ? player.getBalance() : 0;
    }

    /**
     * Выполнить дебетовую операцию для пользователя.
     *
     * @param username      Имя пользователя.
     * @param transactionId Идентификатор транзакции.
     * @param amount        Сумма транзакции.
     * @throws Exception если пользователь не найден или транзакция с данной id уже существует.
     */
    public void debit(String username, String transactionId, double amount) throws Exception {
        Player player = playerRepository.getPlayer(username);
        if (player == null) {
            throw new Exception("Игрок не найден");
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

    /**
     * Выполнить кредитовую операцию для пользователя.
     *
     * @param username      Имя пользователя.
     * @param transactionId Идентификатор транзакции.
     * @param amount        Сумма транзакции.
     * @throws Exception если пользователь не найден или транзакция с данной id уже существует.
     */
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

    /**
     * Получить историю транзакций пользователя.
     *
     * @param username Имя пользователя.
     * @return Список транзакций пользователя, если пользователь найден, новый пустой список в противном случае.
     */
    public List<Transaction> getTransactionHistory(String username) {
        Player player = playerRepository.getPlayer(username);
        return player != null ? player.getTransactions() : new ArrayList<>();
    }

    /**
     * Выход из системы для пользователя.
     *
     * @param username Имя пользователя.
     */
    public void logout(String username) {
        addAction(username, "Выход из системы", "");
        playerRepository.removePlayer(username);
    }
}
