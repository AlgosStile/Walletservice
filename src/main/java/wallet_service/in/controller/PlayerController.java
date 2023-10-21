package wallet_service.in.controller;

import wallet_service.in.repository.PlayerRepository;
import wallet_service.in.service.PlayerService;
import wallet_service.in.model.Action;


import java.sql.SQLException;
import java.util.List;


/**
 * PlayerController является классом представления, который взаимодействует с
 * пользователем и делегирует другим сервисам и репозиториям выполнение логики приложения.
 *
 * PlayerController предоставляет методы для регистрации, авторизации,
 * получения баланса, выхода из системы и получения списка действий игрока.
 *
 * @author Олег Тодор
 * @since 1.0.0
 */

public class PlayerController {
    private PlayerService playerService;
    private PlayerRepository playerRepository;


    /**
     * Конструктор класса PlayerController.
     *
     * @param playerService     сервис игрока, который обрабатывает логику, связанную с игроком.
     * @param playerRepository  репозиторий игрока, который обеспечивает взаимодействие с базой данных игрока.
     *
     * @throws SQLException     если во время создания объекта произошла ошибка доступа к базе данных.
     */
    public PlayerController(PlayerService playerService, PlayerRepository playerRepository) throws SQLException {
        this.playerService = playerService;
        this.playerRepository = playerRepository;
    }


    /**
     * Метод регистрации нового игрока.
     *
     * @param username      имя нового игрока.
     * @param password      пароль нового игрока.
     */
    public void registerPlayer(String username, String password) {
        try {
            playerService.registerPlayer(username, password);
            System.out.println("Игрок успешно зарегистрирован 😀");
        } catch (Exception e) {
            System.out.println("Ошибка при регистрации: " + e.getMessage() + " 😞");
        }
    }


    /**
     * Метод аутентификации игрока.
     *
     * @param username      имя игрока.
     * @param password      пароль игрока.
     *
     * @throws SQLException если произошла ошибка доступа к базе данных.
     */
    public void authenticatePlayer(String username, String password) throws SQLException {
        boolean isAuthenticated = playerService.authenticatePlayer(username, password);

        if (isAuthenticated) {
            System.out.println("Игрок успешно авторизован!");
        } else {
            System.out.println("Неправильное имя пользователя или пароль");
        }
    }


    /**
     * Метод для получения баланса игрока.
     *
     * @param username      имя игрока.
     *
     * @throws SQLException если произошла ошибка доступа к базе данных.
     */
    public void getBalance(String username) throws SQLException {
        double balance = playerRepository.getBalance(username);
        System.out.println("Баланс: " + balance);
    }

    /**
     * Метод для выхода игрока из системы.
     *
     * @param username      имя игрока.
     */
    public void logoutPlayer(String username) {
        try {
            playerRepository.logoutPlayer(username);
            System.out.println("Игрок успешно вышел из системы");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при выходе из системы: " + e.getMessage());
        }
    }


    /**
     * Метод для получения списка действий игрока.
     *
     * @param username      имя игрока.
     *
     * @return список действий игрока.
     */
    public List<Action> getPlayerActions(String username) {
        return playerService.getPlayerActions(username);
    }
}
