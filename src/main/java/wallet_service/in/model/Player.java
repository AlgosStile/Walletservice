package wallet_service.in.model;

import wallet_service.in.controller.TransactionType;

import java.util.ArrayList;
import java.util.List;


/**
 * Класс, представляющий игрока.
 *
 * @author Олег Тодор
 */
public class Player {
    private String username; // Имя пользователя
    private String password; // Пароль
    private double balance; // Баланс
    private List<Transaction> transactions; // Список транзакций

    /**
     * Конструктор класса Player.
     *
     * @param username Имя пользователя
     * @param password Пароль
     */
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    /**
     * Получить имя пользователя.
     *
     * @return Имя пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * Получить пароль.
     *
     * @return Пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * Получить баланс игрока.
     *
     * @return Баланс игрока
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Получить список транзакций игрока.
     *
     * @return Список транзакций игрока
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Добавить транзакцию в список транзакций игрока.
     *
     * @param transaction Транзакция для добавления
     */
    public synchronized void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Выполнить дебетовую транзакцию.
     *
     * @param id     Идентификатор транзакции
     * @param amount Сумма транзакции
     * @throws Exception Если сумма отрицательная или недостаточно средств
     */
    public void debit(String id, double amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Сумма не может быть отрицательной");
        }
        if (amount > this.balance) {
            throw new Exception("Недостаточно средств");
        }

        this.balance -= amount;
        Transaction transaction = new Transaction(id, amount, TransactionType.DEBIT);
        transactions.add(transaction);
    }

    /**
     * Выполнить кредитовую транзакцию.
     *
     * @param id     Идентификатор транзакции
     * @param amount Сумма транзакции
     * @throws Exception Если сумма отрицательная
     */
    public void credit(String id, double amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Некорректная сумма");
        }
        this.balance += amount;
        Transaction transaction = new Transaction(id, amount, TransactionType.CREDIT);
        transactions.add(transaction);
    }
}