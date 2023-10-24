package wallet_service.out.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import wallet_service.in.controller.TransactionType;


/**
 * Класс TransactionDto представляет объект транзакции.
 * Содержит информацию о ID транзакции, сумме, типе транзакции и имени пользователя.
 * Используется для общения с пользователями и передачи данных.
 *
 * @author Олег Тодор
 */
@Getter
public class TransactionDto {
    /**
     * Уникальный идентификатор транзакции.
     * Не может быть пустым, это проверяется с помощью аннотации @NotBlank.
     */
    @NotBlank(message = "Transaction id cannot be blank")
    private String id;

    /**
     * Сумма транзакции.
     * Не может быть null и должна быть больше или равна 0, это проверяется с помощью аннотаций @NotNull и @Min.
     */
    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount should not be less than 0")
    private double amount;

    /**
     * Тип транзакции.
     * Не может быть null, это проверяется с помощью @NotNull.
     */
    @NotNull(message = "Transaction type cannot be null")
    private TransactionType type;

    /**
     * Имя пользователя, связанного с транзакцией.
     * Не может быть пустым, это проверяется с помощью аннотации @NotBlank.
     */
    @NotBlank(message = "Username cannot be blank")
    private String username;

    /**
     * Полный конструктор, инициализирующий все поля класса.
     *
     * @param id идентификатор транзакции.
     * @param amount сумма транзакции.
     * @param type тип транзакции.
     * @param username имя пользователя.
     */
    public TransactionDto(String id, double amount, TransactionType type, String username) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.username = username;
    }

    /**
     * Конструктор по умолчанию.
     * Необходим для правильной работы с фреймворком Jackson.
     */
    public TransactionDto() {
    }

    /**
     * Конструктор инициализирует только часть полей класса: @param id, @param amount и @param type.
     *
     * @param id идентификатор транзакции.
     * @param amount сумма транзакции.
     * @param type тип транзакции.
     */
    public TransactionDto(String id, double amount, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.type = type;
    }

    /**
     * Устанавливает идентификатор транзакции.
     *
     * @param id идентификатор транзакции.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Устанавливает сумму транзакции.
     *
     * @param amount сумма транзакции.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Устанавливает тип транзакции.
     *
     * @param type тип транзакции.
     */
    public void setType(TransactionType type) {
        this.type = type;
    }

    /**
     * Устанавливает имя пользователя, связанного с транзакцией.
     *
     * @param username имя пользователя.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
