package wallet_service.in.controller;

import wallet_service.in.service.PlayerService;
import wallet_service.in.model.Action;


import java.util.List;

public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void registerPlayer(String username, String password) {
        playerService.registerPlayer(username, password);
        System.out.println("Игрок успешно зарегистрировался");
    }

    public boolean authenticatePlayer(String username, String password) {
        boolean isAuthenticated = playerService.authenticatePlayer(username, password);

        if (isAuthenticated) {
            System.out.println("Игрок успешно авторизован!");
        } else {
            System.out.println("Неправильное имя пользователя или пароль");
        }

        return isAuthenticated;
    }

    public double getBalance(String username) {
        double balance = 0;
        if (playerService.isUserRegistered(username) && playerService.isUserAuthenticated(username)) {
            balance = playerService.getBalance(username);
            System.out.println("Баланс: " + balance);
        } else {
            System.out.println("Пользователь не аутентифицирован в системе! Введите регистрационные данные.");
        }

        return balance;
    }


    public void logoutPlayer(String username) {
        playerService.logout(username);
    }

    public List<Action> getPlayerActions(String username) {
        return playerService.getPlayerActions(username);
    }
}

