package wallet_service.in.service;

import wallet_service.in.controller.TransactionType;
import wallet_service.in.model.Action;
import wallet_service.in.model.Player;
import wallet_service.in.model.Transaction;
import wallet_service.in.repository.ActionRepository;
import wallet_service.in.repository.PlayerRepository;
import wallet_service.in.repository.TransactionRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayerServiceImpl реализует интерфейс PlayerService, представляет собой сервис для управления игроками и их транзакциями
 * с возможностями регистрации, авторизации, управления балансом, получения истории транзакций и действий и т.д.
 *
 * @author Олег Тодор
 * @since 1.0.0
 */
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TransactionRepository transactionRepository;
    private final List<Action> actions;
    private String authenticatedUser;
    private final ActionRepository actionRepository;
    /**
     * При создании нового экземпляра класса устанавливаются репозитории:
     * - для игроков playerRepository,
     * - для транзакций transactionRepository,
     * - для совершённых действий actionRepository.
     * Также инициализируется список действий actions и устанавливается null для authenticatedUser.
     *
     * @param playerRepository ссылка на репозиторий игроков
     * @param transactionRepository ссылка на репозиторий транзакций
     * @throws SQLException при проблемах взаимодействия с БД
     */
    public PlayerServiceImpl(PlayerRepository playerRepository, TransactionRepository transactionRepository) throws SQLException {
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
        this.actions = new ArrayList<>();
        this.actionRepository = new ActionRepository();
    }

    /**
     * Создает новое действие и добавляет его в список действий actions
     * и сохраняет в базе данных через репозиторий actionRepository.
     *
     * @param username имя пользователя, совершившего действие
     * @param action название действия
     * @param detail подробное описание действия
     */
    @Override
    public synchronized void addAction(String username, String action, String detail) {
        Action actionObject = new Action(username, action, detail);

        actions.add(actionObject);

        try {
            actionRepository.addAction(actionObject); // Записываем действия в БД
        } catch (SQLException e) {
            // Обработка исключения
            e.printStackTrace();
        }
    }

    /**
     * Получает список действий конкретного игрока.
     *
     * @param username имя пользователя игрока
     * @return список действий игрока
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
     * Регистрирует нового игрока с указанным именем пользователя и паролем,
     * а затем добавляет его в репозиторий игроков.
     *
     * @param username имя нового пользователя
     * @param password пароль нового пользователя
     * @throws SQLException в случае проблем с обращением к базе данных
     */
    public void registerPlayer(String username, String password) throws SQLException {
        Player existingPlayer = playerRepository.getPlayer(username);
        if (existingPlayer != null) {
            throw new RuntimeException("Имя пользователя уже занято!");
        }
        Player player = new Player(username, password);
        playerRepository.addPlayer(player);
        addAction(username, "Регистрация игрока", "");
    }
    /**
     * Пытается аутентифицировать игрока с указанными именем пользователя и паролем.
     * Если игрок аутентифицирован, имя этого игрока становится текущим аутентифицированным пользователем.
     *
     * @param username имя пользователя
     * @param password пароль
     * @throws SQLException при проблемах с обращением к базе данных
     * @return true если имя пользователя и пароль соответствуют зарегистрированному игроку, false в противном случае
     */
    public boolean authenticatePlayer(String username, String password) throws SQLException {
        Player player = playerRepository.getPlayer(username);
        boolean result = player != null && player.getPassword().equals(password);
        if (result) {
            authenticatedUser = username;
        }
        addAction(username, "Аутентификация игрока", result ? "Успешно" : "Неудачно");
        return result;
    }
    /**

     Проверяет, зарегистрирован ли пользователь.
     @param username имя пользователя
     @return true, если пользователь зарегистрирован, иначе false
     @throws SQLException если возникает ошибка при работе с базой данных */
    public boolean isUserRegistered(String username) throws SQLException {
        Player player = playerRepository.getPlayer(username);
        return player != null;
    }
    /**

     Проверяет, является ли пользователь аутентифицированным.
     @param username имя пользователя
     @return true, если пользователь аутентифицирован, иначе false */
    public boolean isUserAuthenticated(String username) {
        return username.equals(authenticatedUser);
    }

    /**
     Возвращает баланс игрока.
     @param username имя пользователя
     @return баланс игрока
     @throws SQLException если возникает ошибка при работе с базой данных
     */
    public double getBalance(String username) throws SQLException {
        Player player = playerRepository.getPlayer(username);
        if (player != null) {
            double newBalance = playerRepository.getBalance(username);
            player.setBalance(newBalance);
            return player.getBalance();
        } else {
            System.out.println("Пользователь не аутентифицирован в системе! Введите регистрационные данные.");
            // тут позже можно вернуть значение по умолчанию.
            return 0.0;
        }
    }
    /**
     Возвращает репозиторий игроков.
     @return репозиторий игроков
     */
    @Override
    public PlayerRepository getPlayerRepository() {
        return playerRepository;
    }

    /**
     * Дебетует счет игрока.
     * @param username     имя пользователя
     * @param transactionId идентификатор транзакции
     * @param amount       сумма транзакции
     * @throws Exception если возникает ошибка в процессе дебетовой операции
     */
    @Override
    public void debit(String username, int transactionId, double amount) throws Exception {
        Player player = playerRepository.getPlayer(username);
        if (player == null) {
            throw new Exception("Игрок не найден");
        }

        // Обновляем баланс игрока из БД перед дебетовой операцией
        double currentBalance = playerRepository.getBalance(username);
        player.setBalance(currentBalance);

        player.debit(transactionId, amount);
        playerRepository.updatePlayer(username, player.getBalance());

        // Обновляем баланс игрока после дебетовой операции
        player.setBalance(playerRepository.getBalance(username));

        Transaction transaction = new Transaction(transactionId, amount, TransactionType.DEBIT);
        transactionRepository.addTransaction(username, transaction);

        player.setBalance(playerRepository.getBalance(username));
    }
    /**
     * Кредитует счет игрока.
     * @param username     имя пользователя
     * @param transactionId идентификатор транзакции
     * @param amount       сумма транзакции
     * @throws Exception если возникает ошибка в процессе кредитовой операции
     */

    @Override
    public void credit(String username, int transactionId, double amount) throws Exception {
        Player player = playerRepository.getPlayer(username);
        if (player == null) {
            throw new Exception("Игрок не найден");
        }

        player.credit(transactionId, amount);
        playerRepository.updatePlayer(username, player.getBalance());

        // Обновляем баланс в объекте Player из БД
        player.setBalance(playerRepository.getBalance(username));

        Transaction transaction = new Transaction(transactionId, amount, TransactionType.CREDIT);
        transactionRepository.addTransaction(username, transaction);

        player.setBalance(playerRepository.getBalance(username));
    }

    /**
    * Возвращает список всех транзакций игрока.
    * @param username имя пользователя
    * @return список всех транзакций игрока
    * @throws SQLException если возникает ошибка при работе с базой данных
     */
    @Override
    public List<Transaction> getTransactionHistory(String username) throws SQLException {
        if (authenticatedUser == null || !authenticatedUser.equals(username)) {
            throw new RuntimeException("Пользователь не аутентифицирован. Введите регистрационные данные.");
        }
        return transactionRepository.getAllTransactions(username);
    }

    /**
     * Выходит из системы.
     *  @param username имя пользователя
     */
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