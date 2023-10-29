package wallet_service.out.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

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

    public Transaction(double amount, String type, int balance) {
        this.amount = amount;
        this.type = type;
        this.balance = balance;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }
}