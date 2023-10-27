package wallet_service.out.service;


import wallet_service.in.model.Action;
import wallet_service.in.model.Transaction;
import wallet_service.out.dto.PlayerDto;

import java.util.List;

/**
 * Интерфейс PlayerService предоставляет собой сервис для управления и работы с игроками.
 * Методы включают в себя различные действия с игроками, такие как регистрация, аутентификация, получение баланса,
 * проведение дебетовых и кредитовых транзакций, истории транзакций и другие операции.
 *
 * @author Олег Тодор
 */
public interface PlayerService {


    /**
     * Добавляет действие для указанного пользователя.
     *
     * @param username - имя пользователя, для которого будет добавлено действие.
     * @param action - действие, которое предстоит добавить.
     * @param detail - детальное описание действия.
     */
    void addAction(String username, String action, String detail);

    /**
     * Получает все действия указанного пользователя.
     *
     * @param username - имя пользователя, для которого нужно получить действия.
     * @return список действий.
     */
    List<Action> getPlayerActions(String username);

    /**
     * Регистрирует игрока с заданным именем пользователя и паролем.
     *
     * @param username - имя пользователя.
     * @param password - пароль пользователя.
     */
    void registerPlayer(String username, String password);

    /**
     * Аутентифицирует игрока с указанным именем пользователя и паролем.
     *
     * @param username - имя пользователя.
     * @param password - пароль пользователя.
     * @return возвращает true, если аутентификация прошла успешно, и false в обратном случае.
     */
    boolean authenticatePlayer(String username, String password);

    /**
     * Получает баланс пользователя.
     *
     * @param username - имя пользователя.
     * @return текущий баланс пользователя.
     */
    double getBalance(String username);

    /**
     * Проверяет, зарегистрирован ли пользователь.
     *
     * @param username - имя пользователя.
     * @return возвращает true, если пользователь зарегистрирован, и false в обратном случае.
     */
    boolean isUserRegistered(String username);

    /**
     * Проверяет, прошел ли пользователь аутентификацию.
     *
     * @param username - имя пользователя.
     * @return возвращает true, если пользователь аутентифицирован, и false в обратном случае.
     */
    boolean isUserAuthenticated(String username);

    /**
     * Дебетует аккаунт пользователя на указанную сумму.
     *
     * @param username - имя пользователя.
     * @param transactionId - ID транзакции.
     * @param amount - сумма, на которую дебетуется аккаунт.
     * @throws Exception при попытке дебетования аккаунта на сумму больше текущего баланса.
     */
    void debit(String username, String transactionId, double amount) throws Exception;

    /**
     * Кредитует аккаунт пользователя на указанную сумму.
     *
     * @param username - имя пользователя.
     * @param transactionId - ID транзакции.
     * @param amount - сумма, на которую кредитуется аккаунт.
     * @throws Exception при попытке кредитования аккаунта на отрицательную сумму.
     */
    void credit(String username, String transactionId, double amount) throws Exception;

    /**
     * Возвращает историю транзакций указанного пользователя.
     *
     * @param username - имя пользователя.
     * @return список транзакций.
     */
    List<Transaction> getTransactionHistory(String username);

    /**
     * Выходит из системы под указанным пользователем.
     *
     * @param username - имя пользователя.
     */
    void logout(String username);

    /**
     * Возвращает информацию о указанном пользователе.
     *
     * @param username - имя пользователя.
     * @return DTO игрока.
     */
    PlayerDto getPlayer(String username);

    /**
     * Выходит из системы под указанным пользователем.
     *
     * @param username - имя пользователя.
     */
    void logoutPlayer(String username);

    /**
     * Обновляет информацию о пользователе.
     *
     * @param username - имя пользователя.
     * @param password - новый пароль пользователя.
     */
    void updatePlayer(String username, String password);
}

