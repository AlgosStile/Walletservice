package wallet_service.out.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Getter
    @Column(name = "balance")
    private int balance;
    public Player() {
    }
    public Player(String username, String password, int balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public Player(String username, String password, BigDecimal zero) {
    }
}