package wallet_service.in.controller;

import wallet_service.in.model.Player;
import wallet_service.in.repository.PlayerRepository;
import wallet_service.in.service.PlayerService;
import wallet_service.in.model.Action;


import java.sql.SQLException;
import java.util.List;


public class PlayerController {


    private PlayerService playerService;

    private PlayerRepository playerRepository;


    public PlayerController(PlayerService playerService) throws SQLException {
        this.playerService = playerService;
        this.playerRepository = new PlayerRepository();
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
        Player player = playerRepository.getPlayer(username);
        if (player != null) {
            System.out.println("Баланс: " + player.getBalance());
        } else {
            System.out.println("Пользователь не аутентифицирован в системе! Введите регистрационные данные.");
        }
    }


    public void logoutPlayer(String username) {
        playerService.logout(username);
    }


    public List<Action> getPlayerActions(String username) {
        return playerService.getPlayerActions(username);
    }
}