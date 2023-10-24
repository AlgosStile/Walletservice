package wallet_service.out.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import wallet_service.in.model.Action;
import wallet_service.out.dto.ActionDto;
import wallet_service.out.dto.PlayerDto;
import wallet_service.out.mapper.ActionMapper;
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
import java.util.Set;
import java.util.stream.Collectors;

import wallet_service.out.util.JwtProvider;

@WebServlet({"/player"})
public class PlayerController extends HttpServlet {
    private JwtProvider jwtProvider;
    private PlayerService playerService;
    private ObjectMapper objectMapper;
    private HttpServletRequest req;
    private HttpServletResponse resp;

    public PlayerController() {
        // конструктор без параметров для web.xml
    }

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void init() {
        this.playerService = new PlayerServiceImpl();
        this.objectMapper = new ObjectMapper();
        this.jwtProvider = new JwtProvider();
    }
    private void validate(Object object) throws ServletException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            throw new ServletException("Validation errors: " + violations);
        }
    }

    private String readJsonFromRequest(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        List<Action> actions = playerService.getPlayerActions(username);

        List<ActionDto> actionDtos = actions.stream()
                .map(action -> ActionMapper.INSTANCE.actionToActionDto(action))
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        String actionsJson = objectMapper.writeValueAsString(actionDtos);

        resp.setContentType("application/json");
        resp.getWriter().print(actionsJson);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        PlayerDto playerDto = objectMapper.readValue(req.getReader(), PlayerDto.class);

        try {
            switch (action) {
                case "register":
                    playerService.registerPlayer(playerDto.getUsername(), playerDto.getPassword());
                    returnTokenAndStatus(resp, HttpServletResponse.SC_CREATED, playerDto); // 201 Created
                    break;
                case "login":
                    boolean authStatus = playerService.authenticatePlayer(playerDto.getUsername(), playerDto.getPassword());
                    if (authStatus)
                        returnTokenAndStatus(resp, HttpServletResponse.SC_OK, playerDto); // 200 OK
                    else
                        returnErrorAndStatus(resp, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // 401 Unauthorized
                    break;
                default:
                    returnErrorAndStatus(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid Action"); // 400 Bad Request
                    break;
            }
        } catch (RuntimeException e) {
            returnErrorAndStatus(resp, HttpServletResponse.SC_BAD_REQUEST, "An error occurred: " + e.getMessage()); // 400 Bad Request
        }
    }

    private void returnTokenAndStatus(HttpServletResponse resp, int status, PlayerDto playerDto) throws IOException {
        String token = jwtProvider.generateToken(playerDto.getUsername());
        resp.setStatus(status);
        resp.setHeader("Authorization", "Bearer " + token);
    }

    private void returnErrorAndStatus(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        String jsonResponse = objectMapper.writeValueAsString(response);
        resp.getWriter().write(jsonResponse);
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = readJsonFromRequest(req);
        ObjectMapper objectMapper = new ObjectMapper();
        PlayerDto playerDto = objectMapper.readValue(json, PlayerDto.class);
        validate(playerDto);

        try {
            playerService.updatePlayer(playerDto.getUsername(), playerDto.getPassword());
            resp.setStatus(HttpServletResponse.SC_OK);
            String jsonResponse = objectMapper.writeValueAsString(playerDto);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonResponse);
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            String jsonResponse = objectMapper.writeValueAsString(response);
            resp.getWriter().write(jsonResponse);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = readJsonFromRequest(req);
        String username = req.getParameter("username");
        playerService.logoutPlayer(username);
        resp.setStatus(HttpServletResponse.SC_OK); // Устанавливаем код ответа 200 OK если выход прошел успешно
    }


    private PlayerDto getPlayerDto(String username) throws ServletException {
        PlayerDto playerDto = playerService.getPlayer(username);
        validate(playerDto);
        return playerDto;
    }


    public void registerPlayer(String username, String password) {
        playerService.registerPlayer(username, password);
        System.out.println("Игрок успешно зарегистрировался");
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

    public void authenticatePlayer(String username, String password) {
        playerService.authenticatePlayer(username, password);
    }
}