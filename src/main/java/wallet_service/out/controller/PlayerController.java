package wallet_service.out.controller;

import wallet_service.out.service.PlayerService;
import wallet_service.out.model.Action;


import java.util.List;

/**
 * Класс PlayerController является контроллером для обработки запросов игрока.
 * Он использует сервис PlayerService для выполнения операций с игроками.
 */
public class PlayerController {
    private final PlayerService playerService;

    /**
     * Конструктор класса PlayerController, который принимает в качестве параметра объект PlayerService.
     * @param playerService объект сервиса для выполнения операций с игроками
     */
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Метод registerPlayer используется для регистрации нового игрока.
     * В случае успешной регистрации выводится сообщение об успехе, в противном случае выводится сообщение об ошибке.
     * @param username имя пользователя, которое будет использоваться при регистрации
     * @param password пароль, который будет использоваться при регистрации
     */
    public void registerPlayer(String username, String password) {
        playerService.registerPlayer(username, password);
        System.out.println("Игрок успешно зарегистрировался");
    }

    /**
     * Метод authenticatePlayer используется для аутентификации игрока.
     * В случае успешной аутентификации выводится сообщение об успехе, в противном случае выводится сообщение об ошибке.
     * @param username имя пользователя, который будет использоваться для аутентификации
     * @param password пароль, который будет использоваться для аутентификации
     */
    public void authenticatePlayer(String username, String password) {
        boolean isAuthenticated = playerService.authenticatePlayer(username, password);

        if (isAuthenticated) {
            System.out.println("Игрок успешно авторизован!");
        } else {
            System.out.println("Неправильное имя пользователя или пароль");
        }
    }

    /**
     * Метод getBalance используется для получения баланса игрока.
     * В случае успешного получения баланса выводится сообщение с его значением, в противном случае выводится сообщение об ошибке.
     * @param username имя пользователя, для которого нужно получить баланс
     */
    public void getBalance(String username) {
        double balance = 0;
        if (playerService.isUserRegistered(username) && playerService.isUserAuthenticated(username)) {
            balance = playerService.getBalance(username);
            System.out.println("Баланс: " + balance);
        } else {
            System.out.println("Пользователь не аутентифицирован в системе! Введите регистрационные данные.");
        }
    }

    /**
     * Метод logoutPlayer используется для выхода игрока из системы.
     * @param username имя пользователя, который будет выходить из системы
     */
    public void logoutPlayer(String username) {
        // Вызываем метод logout из объекта playerService для выхода игрока из системы
        playerService.logout(username);
    }

    /**
     * Метод getPlayerActions используется для получения списка действий игрока.
     * @param username имя пользователя, для которого нужно получить список действий
     * @return список объектов Action, содержащих информацию о действиях игрока
     */
    public List<Action> getPlayerActions(String username) {
        return playerService.getPlayerActions(username);
    }
}