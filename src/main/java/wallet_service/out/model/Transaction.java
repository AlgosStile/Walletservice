package wallet_service.out.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "amount")
    private double amount;

    @Column(name = "type")
    private String type;

    @Column(name = "balance")
    private int balance;

    public Transaction(String username, double amount, String type, int balance) {
        this.username = username;
        this.amount = amount;
        this.type = type;
        this.balance = balance;


    }


}