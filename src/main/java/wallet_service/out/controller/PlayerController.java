package wallet_service.out.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import wallet_service.in.model.Action;
import wallet_service.out.dto.PlayerDto;
import wallet_service.out.service.PlayerService;
import wallet_service.out.service.PlayerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/player")
public class PlayerController extends HttpServlet {
    private PlayerService playerService;
    private ObjectMapper objectMapper;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void init() {
        this.playerService = new PlayerServiceImpl();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        String username = req.getParameter("username");
        PlayerDto playerDto = getPlayerDto(username);

        ObjectMapper objectMapper = new ObjectMapper();
        String playerJson = objectMapper.writeValueAsString(playerDto);

        resp.setContentType("application/json");
        resp.getWriter().print(playerJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        PlayerDto playerDto = objectMapper.readValue(reader, PlayerDto.class);

        try {
            playerService.registerPlayer(playerDto.getUsername(), playerDto.getPassword());
            resp.setStatus(HttpServletResponse.SC_CREATED); // Устанавливаем код ответа 201 Created
            String playerJson = objectMapper.writeValueAsString(playerDto);
            resp.setContentType("application/json");
            resp.getWriter().write(playerJson);
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Устанавливаем код ответа 400 Bad Request если регистрация не удалась
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            String jsonResponse = objectMapper.writeValueAsString(response);
            resp.getWriter().write(jsonResponse);
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        PlayerDto playerDto = objectMapper.readValue(reader, PlayerDto.class);

        try {
            playerService.updatePlayer(playerDto.getUsername(), playerDto.getPassword());
            resp.setStatus(HttpServletResponse.SC_OK); // Устанавливаем код ответа 200 OK если обновление успешно
            String playerJson = objectMapper.writeValueAsString(playerDto);
            resp.setContentType("application/json");
            resp.getWriter().write(playerJson);
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Устанавливаем код ответа 400 Bad Request если обновление не удалось
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            String jsonResponse = objectMapper.writeValueAsString(response);
            resp.getWriter().write(jsonResponse);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        playerService.logoutPlayer(username);
        resp.setStatus(HttpServletResponse.SC_OK); // Устанавливаем код ответа 200 OK если выход прошел успешно
    }


    private PlayerDto getPlayerDto(String username) {
        PlayerDto playerDto = playerService.getPlayer(username);
        return playerDto;
    }


    public void registerPlayer(String username, String password) {
        playerService.registerPlayer(username, password);
        System.out.println("Игрок успешно зарегистрировался");
    }


    public void authenticatePlayer(String username, String password) {
        boolean isAuthenticated = playerService.authenticatePlayer(username, password);

        if (isAuthenticated) {
            System.out.println("Игрок успешно авторизован!");
        } else {
            System.out.println("Неправильное имя пользователя или пароль");
        }
    }


    public void getBalance(String username) {
        double balance = 0;
        if (playerService.isUserRegistered(username) && playerService.isUserAuthenticated(username)) {
            balance = playerService.getBalance(username);
            System.out.println("Баланс: " + balance);
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