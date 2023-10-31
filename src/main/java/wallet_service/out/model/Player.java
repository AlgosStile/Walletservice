package wallet_service.out.model;


import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * Класс представляет сущность "Игрок".
 * Содержит информацию о игроке, такую как имя пользователя, пароль и баланс.
 */
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
    /**
     * Конструктор класса Player с параметрами.
     *
     * @param username имя пользователя игрока
     * @param password пароль игрока
     * @param balance  баланс игрока
     */
    public Player(String username, String password, int balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }
}