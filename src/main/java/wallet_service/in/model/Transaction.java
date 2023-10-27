package wallet_service.in.model;

import wallet_service.in.controller.TransactionType;


/**
 * Класс Transaction представляет транзакцию с ее данными.
 * @author Олег Тодор
 * @since 1.0.0
 */
public class Transaction {
    private int id;
    private double amount;
    private TransactionType type;

    /**
     * Конструктор класса Transaction с указанным идентификатором, суммой и типом транзакции.
     *
     * @param id     идентификатор транзакции
     * @param amount сумма транзакции
     * @param type   тип транзакции
     */
    public Transaction(int id, double amount, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.type = type;
    }

    /**
     * Возвращает идентификатор транзакции.
     *
     * @return идентификатор транзакции
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает сумму транзакции.
     *
     * @return сумма транзакции
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Возвращает тип транзакции.
     *
     * @return тип транзакции
     */
    public TransactionType getType() {
        return type;
    }
}
