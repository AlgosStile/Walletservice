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
/**
 * Класс контроллера игрока, обрабатывает HTTP запросы, связанные с игроками, используя {@link HttpServlet}.
 * Реализует сервлет, который отвечает на запросы на("/player").
 *
 * @author Олег Тодор
 * @see HttpServlet
 */
@WebServlet({"/player"})
public class PlayerController extends HttpServlet {
    private JwtProvider jwtProvider;
    private PlayerService playerService;
    private ObjectMapper objectMapper;
    private HttpServletRequest req;
    private HttpServletResponse resp;

    /**
     * Конструктор по умолчанию, используемый для web.xml.
     */
    public PlayerController() {

    }

    /**
     * Конструктор с параметром, используемый для инъекции {@link PlayerService}.
     *
     * @param playerService сервис игроков, который будет использован контроллером.
     */
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Инициализация контроллера игрока, создание экземпляра {@link PlayerServiceImpl},
     * {@link ObjectMapper} и {@link JwtProvider}.
     */
    public void init() {
        this.playerService = new PlayerServiceImpl();
        this.objectMapper = new ObjectMapper();
        this.jwtProvider = new JwtProvider();
    }

    /**
     * Проверка пришедших объектов на нарушение ограничений,
     * определенных аннотациями валидации в классах моделей.
     *
     * @param object объект для валидации.
     * @throws ServletException если есть нарушения, бросается исключение.
     */
    private void validate(Object object) throws ServletException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            throw new ServletException("Validation errors: " + violations);
        }
    }

    /**
     * Метод для загрузки JSON из запроса.
     *
     * @param req HttpServletRequest запрос, из которого следует прочесть JSON.
     * @throws IOException в случае ошибки при чтении из запроса выбрасывается исключение.
     */
    private String readJsonFromRequest(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * Метод обработки HTTP GET запросов. Используется для получения
     * информации об действиях игрока.
     *
     * @param req  HttpServletRequest запрос.
     * @param resp HttpServletResponse ответ.
     * @throws ServletException в случае ошибки валдации или получения действий игрока.
     * @throws IOException      в случае ошибки при создании JSON ответа.
     */

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

    /**
     * Метод обработки HTTP POST запросов. Используется для выполнения
     * действий, связанных с состоянием аутентификации игрока.
     *
     * @param req  HttpServletRequest запрос.
     * @param resp HttpServletResponse ответ.
     * @throws ServletException в случае ошибки валдации или авторизации игрока.
     * @throws IOException      в случае ошибки при создании JSON ответа.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PlayerDto playerDto = objectMapper.readValue(req.getReader(), PlayerDto.class);

        try {
            if (req.getServletPath().equals("/player/register")) {
                playerService.registerPlayer(playerDto.getUsername(), playerDto.getPassword());
                try {
                    returnTokenAndStatus(resp, HttpServletResponse.SC_CREATED, playerDto); // 201 Created
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (req.getServletPath().equals("/player/login")) {
                boolean authStatus = playerService.authenticatePlayer(playerDto.getUsername(), playerDto.getPassword());
                if (authStatus) {
                    try {
                        returnTokenAndStatus(resp, HttpServletResponse.SC_OK, playerDto); // 200 OK
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        returnErrorAndStatus(resp, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // 401 Unauthorized
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else {
                try {
                    returnErrorAndStatus(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid URL"); // 400 Bad Request
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (RuntimeException e) {
            returnErrorAndStatus(resp, HttpServletResponse.SC_BAD_REQUEST, "An error occurred: " + e.getMessage()); // 400 Bad Request
        }
    }

    /**
     * Метод, возвращающий токен и статус.
     *
     * @param resp      HttpServletResponse ответ.
     * @param status    статус ответа.
     * @param playerDto объект данных игрока.
     * @throws IOException если проблема с выходным потоком.
     */
    private void returnTokenAndStatus(HttpServletResponse resp, int status, PlayerDto playerDto) throws IOException {
        String token = jwtProvider.generateToken(playerDto.getUsername());
        resp.setStatus(status);
        resp.setHeader("Authorization", "Bearer " + token);
    }


    /**
     * Метод возвращает код ошибки и сообщение об ошибке.
     *
     * @param resp    HttpServletResponse.
     * @param status  код ошибки.
     * @param message сообщение об ошибке.
     * @throws IOException если проблема с выходным потоком.
     */
    private void returnErrorAndStatus(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        String jsonResponse = objectMapper.writeValueAsString(response);
        resp.getWriter().write(jsonResponse);
    }


    /**
     * Метод обработки HTTP PUT запросов. Используется для обновления
     * информацию об игроке.
     *
     * @param req  HttpServletRequest запрос.
     * @param resp HttpServletResponse ответ.
     * @throws ServletException в случае ошибки валдации или обновления информации игрока.
     * @throws IOException      в случае ошибки при создании JSON ответа.
     */

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


    /**
     * Метод обработки HTTP DELETE запросов. Используется для выхода
     * игрока из системы.
     *
     * @param req  HttpServletRequest запрос.
     * @param resp HttpServletResponse ответ.
     * @throws ServletException в случае ошибки валдации или выхода игрока.
     * @throws IOException      в случае ошибки при создании JSON ответа.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = readJsonFromRequest(req);
        String username = req.getParameter("username");
        playerService.logoutPlayer(username);
        resp.setStatus(HttpServletResponse.SC_OK); // Устанавливаем код ответа 200 OK если выход прошел успешно
    }


    /**
     * Преобразует имя пользователя в {@link PlayerDto}
     *
     * @param username имя пользователя для поиска в системе.
     * @return возвращает {@link PlayerDto}, соответствующий имени пользователя.
     * @throws ServletException Если именя пользователя не существует в системе
     */
    private PlayerDto getPlayerDto(String username) throws ServletException {
        PlayerDto playerDto = playerService.getPlayer(username);
        validate(playerDto);
        return playerDto;
    }


    /**
     * Регистрирует нового игрока в системе.
     *
     * @param username имя пользователя игрока.
     * @param password пароль игрока.
     */
    public void registerPlayer(String username, String password) {
        playerService.registerPlayer(username, password);
        System.out.println("Игрок успешно зарегистрировался");
    }


    /**
     * Выводит баланс для заданного пользователя, если он зарегистрирован и аутентифицирован.
     *
     * @param username имя пользователя, для которого нужно получить баланс.
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
     * Выход игрока из системы.
     *
     * @param username имя пользователя, который выходит из системы.
     */
    public void logoutPlayer(String username) {
        playerService.logout(username);
    }


    /**
     * Возвращает список действий, выполненных игроком.
     *
     * @param username имя игрока, чьи действия следует получить.
     * @return список действий игрока.
     */
    public List<Action> getPlayerActions(String username) {
        return playerService.getPlayerActions(username);
    }


    /**
     * Аутентифицирует игрока в системе.
     *
     * @param username имя игрока для аутентификации.
     * @param password пароль игрока.
     */
    public void authenticatePlayer(String username, String password) {
        playerService.authenticatePlayer(username, password);
    }
}