package main.java.org.wallet_service.out.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Класс Player представляет игрока, зарегистрированного в системе.
 * Он содержит информацию об идентификаторе игрока, его имени пользователя,
 * пароле и балансе.
 *
 * @author Олег Тодор
 */
@Setter
@Getter
@Entity
@Table(name = "players")
public class Player {
    /**
     * Идентификатор игрока.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Имя пользователя игрока.
     */
    @Column(name = "username")
    private String username;

    /**
     * Пароль игрока.
     */
    @Column(name = "password")
    private String password;

    /**
     * Баланс игрока.
     */
    @Getter
    @Column(name = "balance")
    private int balance;

    public Player() {
    }

    /**
     * Конструктор класса Player.
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

    /**
     * Конструктор класса Player с использованием BigDecimal.
     *
     * @param username имя пользователя игрока
     * @param password пароль игрока
     * @param zero     значение BigDecimal (не используется)
     */
    public Player(String username, String password, BigDecimal zero) {
    }
}
