package wallet_service.out.dto;

public class PlayerDto {
    private String username;
    private double balance;
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