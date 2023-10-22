package wallet_service.out.dto;


import wallet_service.out.controller.TransactionType;

public class TransactionDto {
    private String id;
    private double amount;
    private TransactionType type;

    public TransactionDto(String id, double amount, TransactionType type) {
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
