package wallet_service.in.model;

import wallet_service.in.controller.TransactionType;


public class Transaction {
    private int id; // Идентификатор транзакции
    private double amount; // Сумма транзакции
    private TransactionType type; // Тип транзакции


    public Transaction(int id, double amount, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.type = type;

    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }
}