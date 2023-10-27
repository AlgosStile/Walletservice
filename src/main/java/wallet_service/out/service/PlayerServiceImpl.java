package wallet_service.out.service;

import wallet_service.in.controller.TransactionType;
import wallet_service.in.model.Action;
import wallet_service.in.model.Player;
import wallet_service.in.model.Transaction;
import wallet_service.out.dto.PlayerDto;
import wallet_service.in.repository.PlayerRepository;
import wallet_service.in.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Класс сервиса для работы с игроками. Имплементирует интерфейс PlayerService.
 *
 * @author Олег Тодор
 */
public class PlayerServiceImpl implements PlayerService {
    private PlayerRepository playerRepository;
    private TransactionRepository transactionRepository;
    private List<Action> actions;
    private String authenticatedUser;

    /**
     * Конструктор класса с передачей связанных репозиториев.
     *
     * @param playerRepository Репозиторий игроков.
     * @param transactionRepository Репозиторий транзакций.
     */
    public PlayerServiceImpl(PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
        this.actions = new CopyOnWriteArrayList<>();
    }

    /**
     * Конструктор класса без указания связанных репозиториев. Инициализирует свои собственные экземпляры репозиториев.
     */
    public PlayerServiceImpl() {
        this.playerRepository = new PlayerRepository();
        this.transactionRepository = new TransactionRepository();
        this.actions = new ArrayList<>();
    }

    /**
     * Добавление действия для игрока.
     *
     * @param username Имя пользователя.
     * @param action Действие.
     * @param detail Детали действия.
     */
    public synchronized void addAction(String username, String action, String detail) {
        actions.add(new Action(username, action, detail));
    }


    /**
     * Получение списка действий конкретного игрока.
     *
     * @param username Имя пользователя.
     * @return Список действий игрока.
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
     * Регистрация игрока.
     *
     * @param username Имя пользователя/логин.
     * @param password Пароль пользователя.
     * @throws RuntimeException Исключение, если игрок с таким именем уже существует.
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
     * Метод аутентификации игрока.
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @return True, если аутентификация успешная. False, если данные не верны.
     */
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


    /**
     * Метод получения баланса игрока.
     *
     * @param username Имя пользователя.
     * @return Баланс игрока.
     * @throws RuntimeException Если пользователь не аутентифицирован.
     */
    @Override
    public double getBalance(String username) {
        if (authenticatedUser == null || !authenticatedUser.equals(username)) {
            throw new RuntimeException("Пользователь не аутентифицирован. Введите регистрационные данные.");
        }
        Player player = playerRepository.getPlayer(username);
        return player != null ? player.getBalance() : 0;
    }


    /**
     * Уменьшает баланс игрока на указанную сумму.
     *
     * @param username       имя игрока
     * @param transactionId  идентификатор транзакции
     * @param amount         сумма транзакции
     * @throws Exception если игрок не найден, средств на счету недостаточно или транзакция с данным идентификатором уже существует
     */
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

    /**
     * Увеличивает баланс игрока на указанную сумму.
     *
     * @param username       имя игрока
     * @param transactionId  идентификатор транзакции
     * @param amount         сумма транзакции
     * @throws Exception если игрок не найден или транзакция с данным идентификатором уже существует
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
     * Возвращает историю транзакций указанного игрока.
     *
     * @param username имя игрока
     * @return         список с транзакциями игрока
     * @throws RuntimeException если пользователь не аутентифицирован
     */
    @Override
    public List<Transaction> getTransactionHistory(String username) {
        if (authenticatedUser == null || !authenticatedUser.equals(username)) {
            throw new RuntimeException("Пользователь не аутентифицирован. Введите регистрационные данные.");
        }
        Player player = playerRepository.getPlayer(username);
        return player != null ? new ArrayList<>(player.getTransactions()) : new ArrayList<>();
    }

    /**
     * Выполняет выход указанного игрока из системы.
     *
     * @param username имя игрока
     */
    public void logout(String username) {
        addAction(username, "Выход из системы", "");
        playerRepository.removePlayer(username);
        authenticatedUser = null;
    }

    /**
     * Возвращает данные игрока по указанному имени.
     *
     * @param username имя игрока
     * @return         данные игрока
     */
    @Override
    public PlayerDto getPlayer(String username) {
        return null;
    }

    /**
     * Выполняет выход указанного игрока из системы.
     *
     * @param username имя игрока
     */
    @Override
    public void logoutPlayer(String username) {
        playerRepository.removePlayer(username);
        authenticatedUser = null;
    }
    /**
     * Обновляет данные указанного игрока в системе.
     *
     * @param username имя игрока
     * @param password новый пароль игрока
     */
    @Override
    public void updatePlayer(String username, String password) {
        playerRepository.updatePlayer(username, password);

    }

}



