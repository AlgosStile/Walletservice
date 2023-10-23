package wallet_service.in.model;

import wallet_service.in.controller.TransactionType;


public class Transaction {
    private String id; // Идентификатор транзакции
    private double amount; // Сумма транзакции
    private TransactionType type; // Тип транзакции


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