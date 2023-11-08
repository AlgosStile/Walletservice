package main.java.org.wallet_service.out.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс Transaction представляет транзакцию, связанную с игровым счетом игрока.
 * Он содержит информацию об идентификаторе транзакции, имени пользователя,
 * сумме, типе транзакции и балансе после проведения транзакции.
 *
 * @author Олег Тодор
 */
@Setter
@Getter
@Entity
@Table(name = "transactions")
public class Transaction {

    /**
     * Идентификатор транзакции.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Имя пользователя, связанное с транзакцией.
     */
    @Column(name = "username")
    private String username;

    /**
     * Сумма транзакции.
     */
    @Column(name = "amount")
    private double amount;

    /**
     * Тип транзакции.
     */
    @Column(name = "type")
    private String type;

    /**
     * Баланс после проведения транзакции.
     */
    @Column(name = "balance")
    private int balance;

    /**
     * Конструктор класса Transaction.
     *
     * @param username имя пользователя, связанное с транзакцией
     * @param amount   сумма транзакции
     * @param type     тип транзакции
     * @param balance  баланс после проведения транзакции
     */
    public Transaction(String username, double amount, String type, int balance) {
        this.username = username;
        this.amount = amount;
        this.type = type;
        this.balance = balance;
    }

    public Transaction() {

    }
}
