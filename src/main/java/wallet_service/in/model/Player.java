package wallet_service.in.model;

import wallet_service.in.controller.TransactionType;
import java.util.ArrayList;
import java.util.List;



/**
 * Класс Player представляет игрока с его данными и операциями.
 * Автор: Олег Тодор
 * Версия: 2.0.0
 */
public class Player {
    private int id;
    private String username;
    private String password;
    private double balance;
    private List<Transaction> transactions;

    /**
     * Конструктор класса Player с указанным именем пользователя и паролем.
     *
     * @param username имя пользователя
     * @param password пароль
     */
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    /**
     * Пустой конструктор класса Player.
     */
    public Player() {
        this.transactions = new ArrayList<>();
    }

    /**
     * Устанавливает идентификатор игрока.
     *
     * @param id идентификатор игрока
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return имя пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * Возвращает список транзакций игрока.
     *
     * @return список транзакций игрока
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Добавляет транзакцию в список транзакций игрока.
     *
     * @param transaction транзакция для добавления
     */
    public synchronized void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Выполняет дебетовую операцию для игрока.
     *
     * @param transactionId идентификатор транзакции
     * @param amount        сумма дебета
     * @throws Exception если произошла ошибка во время выпольнения дебетовой операции
     */
    public void debit(int transactionId, double amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Сумма не может быть отрицательной");
        }
        if (amount > this.balance) {
            throw new Exception("Недостаточно средств");
        }

        this.balance -= amount;
        Transaction transaction = new Transaction(transactionId, amount, TransactionType.DEBIT);
        transactions.add(transaction);
    }

    /**
     * Выполняет кредитную операцию для игрока.
     *
     * @param transactionId идентификатор транзакции
     * @param amount        сумма кредита
     * @throws Exception если произошла ошибка во время выполнения кредитной операции
     */
    public void credit(int transactionId, double amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Некорректная сумма");
        }
        this.balance += amount;
        Transaction transaction = new Transaction(transactionId, amount, TransactionType.CREDIT);
        transactions.add(transaction);
    }

    /**
     * Возвращает баланс игрока.
     *
     * @return баланс игрока
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Устанавливает новое имя пользователя игрока.
     *
     * @param username новое имя пользователя
     */
    public void setUserName(String username) {
        this.username = username;
    }

    /**
     * Устанавливает новый пароль пользователя игрока.
     *
     * @param password новый пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Устанавливает новый баланс игрока.
     *
     * @param newBalance новый баланс
     */
    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }
}
