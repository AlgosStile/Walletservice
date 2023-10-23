package wallet_service.out.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import wallet_service.in.controller.TransactionType;


@Getter
public class TransactionDto {
    @NotBlank(message = "Transaction id cannot be blank")
    private String id;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount should not be less than 0")
    private double amount;

    @NotNull(message = "Transaction type cannot be null")
    private TransactionType type;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    public TransactionDto(String id, double amount, TransactionType type, String username) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.username = username;
    }

    // По умолчанию публичный конструктор для Jackson
    public TransactionDto() {
    }

    public TransactionDto(String id, double amount, TransactionType type) {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
