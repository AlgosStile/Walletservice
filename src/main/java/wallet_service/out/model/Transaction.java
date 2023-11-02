package wallet_service.out.model;

import lombok.Getter;

import javax.persistence.*;

/**
 * Класс представляет сущность "Транзакция".
 * Содержит информацию о транзакции, такую как сумма, тип и баланс.
 */
@Getter
@Entity
@Table(name = "transactions")
public class Transaction {

    /**
     * -- GETTER --
     * Возвращает уникальный идентификатор транзакции.
     *
     * @return уникальный идентификатор транзакции
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private int id;

    /**
     * -- GETTER --
     * Возвращает сумму транзакции.
     *
     * @return сумма транзакции
     */
    @Column(name = "amount")
    private double amount;

    /**
     * -- GETTER --
     * Возвращает тип транзакции.
     *
     * @return тип транзакции
     */
    @Column(name = "type")
    private String type;

    /**
     * -- GETTER --
     * Возвращает баланс после транзакции.
     *
     * @return баланс после транзакции
     */
    @Column(name = "balance")
    private int balance;

    public Transaction() {
    }

    /**
     * Конструктор класса Transaction с параметрами.
     *
     * @param amount  сумма транзакции
     * @param type    тип транзакции
     * @param balance баланс после транзакции
     */
    public Transaction(double amount, String type, int balance) {
        this.amount = amount;
        this.type = type;
        this.balance = balance;
    }

    /**
     * Устанавливает уникальный идентификатор транзакции.
     *
     * @param id уникальный идентификатор транзакции
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Устанавливает сумму транзакции.
     *
     * @param amount сумма транзакции
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Устанавливает тип транзакции.
     *
     * @param type тип транзакции
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Устанавливает баланс после транзакции.
     *
     * @param balance баланс после транзакции
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }
}