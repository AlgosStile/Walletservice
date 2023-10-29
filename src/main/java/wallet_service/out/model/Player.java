package wallet_service.out.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Представляет игрока в системе кошелька.
 *
 * Этот класс представляет игрока в системе кошелька и содержит информацию о его имени пользователя,
 * пароле, балансе и списке транзакций.
 *
 * @author Олег Тодор
 */
public class Player {

    private String username;
    private String password;
    private double balance;
    private List<Transaction> transactions;

    /**
     * Создает экземпляр класса Player с указанным именем пользователя и паролем.
     *
     * @param username Имя пользователя игрока.
     * @param password Пароль игрока.
     */
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    /**
     * Возвращает имя пользователя игрока.
     *
     * @return Имя пользователя игрока.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Возвращает пароль игрока.
     *
     * @return Пароль игрока.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Возвращает текущий баланс игрока.
     *
     * @return Текущий баланс игрока.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Возвращает список транзакций игрока.
     *
     * @return Список транзакций игрока.
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Добавляет новую транзакцию в список транзакций игрока.
     *
     * @param transaction Новая транзакция.
     */
    public synchronized void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Списывает указанную сумму с баланса игрока.
     *
     * @param id     Идентификатор транзакции.
     * @param amount Сумма, которую необходимо списать.
     * @throws Exception Если сумма отрицательна или недостаточно средств на балансе игрока.
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
     * Зачисляет указанную сумму на баланс игрока.
     *
     * @param id     Идентификатор транзакции.
     * @param amount Сумма, которую необходимо зачислить.
     * @throws Exception Если сумма отрицательна.
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