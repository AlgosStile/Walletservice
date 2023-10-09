package wallet_service.model;

import wallet_service.controller.TransactionType;

/**
 * Класс, представляющий транзакцию.
 */
public class Transaction {
    private String id; // Идентификатор транзакции
    private double amount; // Сумма транзакции
    private TransactionType type; // Тип транзакции

    /**
     * Конструктор класса Transaction.
     *
     * @param id     Идентификатор транзакции
     * @param amount Сумма транзакции
     * @param type   Тип транзакции
     */
    public Transaction(String id, double amount, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.type = type;
    }

    /**
     * Получить идентификатор транзакции.
     *
     * @return Идентификатор транзакции
     */
    public String getId() {
        return id;
    }

    /**
     * Получить сумму транзакции.
     *
     * @return Сумма транзакции
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Получить тип транзакции.
     *
     * @return Тип транзакции
     */
    public TransactionType getType() {
        return type;
    }
}