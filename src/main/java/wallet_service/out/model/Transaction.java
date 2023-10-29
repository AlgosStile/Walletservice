package wallet_service.out.model;

import wallet_service.out.controller.TransactionType;

/**
 * Представляет транзакцию в системе кошелька.
 *
 * <p>
 * Этот класс представляет транзакцию в системе кошелька и содержит информацию об идентификаторе транзакции,
 * сумме транзакции и ее типе.
 * </p>
 *
 * @author Олег Тодор
 */
public class Transaction {

    private String id;
    private double amount;
    private TransactionType type;

    /**
     * Создает экземпляр класса Transaction с указанным идентификатором, суммой и типом транзакции.
     *
     * @param id     Идентификатор транзакции.
     * @param amount Сумма транзакции.
     * @param type   Тип транзакции.
     */
    public Transaction(String id, double amount, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.type = type;
    }

    /**
     * Возвращает идентификатор транзакции.
     *
     * @return Идентификатор транзакции.
     */
    public String getId() {
        return id;
    }

    /**
     * Возвращает сумму транзакции.
     *
     * @return Сумма транзакции.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Возвращает тип транзакции.
     *
     * @return Тип транзакции.
     */
    public TransactionType getType() {
        return type;
    }
}