package wallet_service.out.dto;

import jakarta.validation.constraints.*;

public class PlayerDto {
    @NotBlank
    private String username;

    @NotNull
    @Min(0)
    private double balance;

    @NotBlank
    @Size(min = 6, max = 15)
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9])", message = "Password must contain one digit."),
            @Pattern(regexp = "(?=.*[a-z])", message = "Password must contain one lowercase letter."),
            @Pattern(regexp = "(?=.*[A-Z])", message = "Password must contain one uppercase letter."),
            @Pattern(regexp = "(?=\\S+$)", message = "Password must contain no whitespace.")
    })
    private String password;

    public PlayerDto(String username, double balance) {
        this.username = username;
        this.balance = balance;
        this.password = "";
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }
}