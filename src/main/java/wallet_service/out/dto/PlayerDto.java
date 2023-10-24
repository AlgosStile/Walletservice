package wallet_service.out.dto;

import jakarta.validation.constraints.*;

/**
 * Класс PlayerDto предназначен для передачи данных игрока в приложении.
 * Он содержит информацию об имени пользователя, балансе и пароле игрока.
 *
 * @author Олег Тодор
 */
public class PlayerDto {

    /**
     * Имя пользователя. Должно быть не пустым.
     */
    @NotBlank
    private String username;

    /**
     * Баланс игрока. Должен быть не нулевым и больше не 0.
     */
    @NotNull
    @Min(0)
    private double balance;

    /**
     * Пароль пользователя. Должен содержать хотя бы одну цифру, одну строчную
     * и одну прописную букву. Длина пароля должна быть от 6 до 15 символов.
     * Пробелы не допускаются.
     */
    @NotBlank
    @Size(min = 6, max = 15)
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9])", message = "Password must contain one digit."),
            @Pattern(regexp = "(?=.*[a-z])", message = "Password must contain one lowercase letter."),
            @Pattern(regexp = "(?=.*[A-Z])", message = "Password must contain one uppercase letter."),
            @Pattern(regexp = "(?=\\S+$)", message = "Password must contain no whitespace.")
    })
    private String password;

    /**
     * Конструктор класса PlayerDto. Устанавливает имя пользователя и баланс,
     * пароль установлен как пустая строка.
     *
     * @param username имя пользователя
     * @param balance  баланс пользователя
     */
    public PlayerDto(String username, double balance) {
        this.username = username;
        this.balance = balance;
        this.password = "";
    }

    /**
     * Пустой конструктор класса PlayerDto.
     */
    public PlayerDto() {

    }

    /**
     * Возвращает имя пользователя.
     * @return имя пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * Возвращает пароль пользователя.
     * @return пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * Возвращает баланс пользователя.
     * @return баланс пользователя
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Устанавливает новое имя пользователя.
     * @param username новое имя пользователя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Устанавливает новый пароль пользователя.
     * @param password новый пароль пользователя
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
