package wallet_service.in.controller;

import wallet_service.in.model.Transaction;
import wallet_service.in.service.PlayerService;

import java.util.List;

/**
 * Класс TransactionController является контроллером для обработки транзакций игрока.
 * Он использует сервис PlayerService для выполнения операций с транзакциями.
 */
public class TransactionController {

    private final PlayerService playerService;

    /**
     * Конструктор класса TransactionController, который принимает в качестве параметра объект PlayerService.
     *
     * @param playerService объект сервиса для выполнения операций с транзакциями
     */
    public TransactionController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Метод debitTransaction используется для выполнения дебетовой транзакции.
     * В случае успешного выполнения выводится сообщение о успешной транзакции, в противном случае выводится сообщение об ошибке.
     *
     * @param username      имя пользователя, для которого будет выполнена транзакция
     * @param transactionId идентификатор транзакции
     * @param amount        сумма транзакции
     * @throws Exception если транзакция не может быть выполнена
     */
    public void debitTransaction(String username, String transactionId, double amount) throws Exception {
        try {
            playerService.debit(username, transactionId, amount);
            System.out.println("Дебетовая транзакция прошла успешно");
        } catch (Exception e) {
            System.out.println("Дебетовая транзакция не удалась: " + e.getMessage());
        }
    }

    /**
     * Метод creditTransaction используется для выполнения кредитной транзакции.
     * В случае успешного выполнения выводится сообщение о успешной транзакции, в противном случае выводится сообщение об ошибке.
     *
     * @param username      имя пользователя, для которого будет выполнена транзакция
     * @param transactionId идентификатор транзакции
     * @param amount        сумма транзакции
     * @throws Exception если транзакция не может быть выполнена
     */
    public void creditTransaction(String username, String transactionId, double amount) throws Exception {
        try {
            playerService.credit(username, transactionId, amount);
            System.out.println("Кредитная транзакция прошла успешно --> $");
        } catch (Exception e) {
            System.out.println("Кредитная транзакция не удалась ¯\\_(ツ)_/¯ : " + e.getMessage());
        }
    }

    /**
     * Метод getTransactionHistory используется для получения истории транзакций пользователя.
     *
     * @param username имя пользователя, для которого нужно получить историю транзакций
     * @return список объектов Transaction, содержащих информацию о транзакциях пользователя
     */
    public List<Transaction> getTransactionHistory(String username) {
        return playerService.getTransactionHistory(username);
    }

}