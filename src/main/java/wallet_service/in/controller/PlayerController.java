package wallet_service.in.controller;

import wallet_service.in.service.PlayerService;
import wallet_service.in.model.Action;
import wallet_service.in.service.PlayerServiceImpl;


import java.sql.SQLException;
import java.util.List;


public class PlayerController {
    private final PlayerService playerService;


//    public PlayerController(PlayerService playerService) {
//        this.playerService = playerService;
//    }
    public PlayerController(PlayerService playerService) throws SQLException {

        this.playerService = new PlayerServiceImpl();
    }


    public void registerPlayer(String username, String password) {
        try {
            playerService.registerPlayer(username, password);
            System.out.println("Игрок успешно зарегистрирован 😀");
        } catch (Exception e) {
            System.out.println("Ошибка при регистрации: " + e.getMessage() + " 😞");
        }
    }



    public void authenticatePlayer(String username, String password) throws SQLException {
        boolean isAuthenticated = playerService.authenticatePlayer(username, password);

        if (isAuthenticated) {
            System.out.println("Игрок успешно авторизован!");
        } else {
            System.out.println("Неправильное имя пользователя или пароль");
        }
    }


    public void getBalance(String username) throws SQLException {
        double balance = 0;
        if (playerService.isUserRegistered(username) && playerService.isUserAuthenticated(username)) {
            balance = playerService.getBalance(username);
            System.out.println("Баланс: " + balance);
        } else {
            System.out.println("Пользователь не аутентифицирован в системе! Введите регистрационные данные.");
        }
    }


    public void logoutPlayer(String username) {
        // Вызываем метод logout из объекта playerService для выхода игрока из системы
        playerService.logout(username);
    }


    public List<Action> getPlayerActions(String username) {
        return playerService.getPlayerActions(username);
    }
}