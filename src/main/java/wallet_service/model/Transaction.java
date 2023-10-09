package wallet_service.model;

import wallet_service.controller.TransactionType;

public class Transaction {
    private String id;
    private double amount;
    private TransactionType type;

    public Transaction(String id, double amount, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }
}
