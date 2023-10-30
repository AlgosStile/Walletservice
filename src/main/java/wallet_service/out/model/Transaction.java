package wallet_service.out.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;


/**
 * Класс представляет сущность "Транзакция".
 * Содержит информацию о транзакции, такую как сумма, тип и баланс.
 */
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Getter
    @Column(name = "amount")
    private double amount;

    @Getter
    @Column(name = "type")
    private String type;

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
    public void setId(Long id) {
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
     * Возвращает уникальный идентификатор транзакции.
     *
     * @return уникальный идентификатор транзакции
     */
    public Long getId() {
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
    public String getType() {
        return type;
    }



    /**
     * Возвращает баланс после транзакции.
     *
     * @return баланс после транзакции
     */
    public int getBalance() {
        return balance;
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