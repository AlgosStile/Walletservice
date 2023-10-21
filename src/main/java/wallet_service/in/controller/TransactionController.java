package wallet_service.in.controller;

import wallet_service.in.config.DBConnection;
import wallet_service.in.model.Transaction;
import wallet_service.in.repository.PlayerRepository;
import wallet_service.in.repository.TransactionRepository;
import wallet_service.in.service.PlayerService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


/**
 * Класс TransactionController отвечает за управление транзакциями, связанными с игроками.
 * @author Олег Тодор
 * @since 1.0.0
 */
public class TransactionController {
    private PlayerService playerService;
    private PlayerRepository playerRepository;
    private TransactionRepository transactionRepository;
    private Connection connection;

    /**
     * Конструктор класса TransactionController с указанными зависимостями.
     *
     * @param playerService        сервис для выполнения операций с игроками
     * @param playerRepository     репозиторий для доступа к данным игроков
     * @param transactionRepository репозиторий для доступа к данным транзакций
     */
    public TransactionController(PlayerService playerService, PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerService = playerService;
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
        this.connection = DBConnection.getInstance().getConnection();
    }

    /**
     * Выполняет дебетовую транзакцию для игрока.
     *
     * @param username имя пользователя игрока
     * @param id       идентификатор игрока
     * @param amount   сумма дебета
     * @throws Exception если произошла ошибка во время выполнения дебетовой транзакции
     */
    public void debitTransaction(String username, int id, double amount) throws Exception {
        try {
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }
            playerService.debit(username, id, amount);
            System.out.println("Дебетовая транзакция прошла успешно --> $");
//            connection.commit(); // Коммит дебетовой транзакции здесь
        } catch (Exception exception) {
            if (connection != null) {
                try {
                    System.err.print("Транзакция откатывается");
                    connection.rollback();
                } catch (SQLException rollbackExcep) {
                    System.out.println("Не удалось выполнить откат транзакции: " + rollbackExcep.getMessage());
                }
            }
            throw exception;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }

    /**
     * Выполняет кредитную транзакцию для игрока.
     *
     * @param username имя пользователя игрока
     * @param id       идентификатор игрока
     * @param amount   сумма кредита
     * @throws Exception если произошла ошибка во время выполнения кредитной транзакции
     */
    public void creditTransaction(String username, int id, double amount) throws Exception {
        try {
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }
            playerService.credit(username, id, amount);
            System.out.println("Кредитная транзакция прошла успешно --> $");
//            connection.commit(); // Коммит кредитной транзакции здесь
        } catch (Exception exception) {
            if (connection != null) {
                try {
                    System.err.print("Транзакция откатывается");
                    connection.rollback();
                } catch (SQLException rollbackExcep) {
                    System.out.println("Не удалось выполнить откат транзакции: " + rollbackExcep.getMessage());
                }
            }
            String message = "Кредитная транзакция завершилась неудачно. Возникла ошибка: " + exception.getMessage();
            System.err.println(message);
            throw exception;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException setAutoCommitExcep) {
                    System.out.println("Не удалось установить автоматическое подтверждение: " + setAutoCommitExcep.getMessage());
                }
            }
        }
    }

    /**
     * Возвращает историю транзакций для игрока.
     *
     * @param username имя пользователя игрока
     * @return список транзакций, представляющих историю транзакций игрока
     * @throws SQLException если произошла ошибка при получении истории транзакций
     */
    public List<Transaction> getTransactionHistory(String username) throws SQLException {
        return playerService.getTransactionHistory(username);
    }
}
