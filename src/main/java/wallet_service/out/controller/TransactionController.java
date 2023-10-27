package wallet_service.out.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import wallet_service.in.model.Transaction;
import wallet_service.out.service.PlayerService;

import java.util.List;

import wallet_service.out.dto.TransactionDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Класс {@code TransactionController}, контроллер транзакций.
 * Отвечает за обработку HTTP-запросов к сервлету "/transaction".
 * Обеспечивает выполнение базовых операций транзакций.
 *
 * @author Олег Тодор
 * @see HttpServlet
 */
@WebServlet({"/transaction"})
public class TransactionController extends HttpServlet {

    private PlayerService playerService;
    private ObjectMapper objectMapper;

    /**
     * Конструктор без параметров.
     * Обычно используется в файле web.xml.
     */
    public TransactionController() {
        // Конструктор без параметров для web.xml
    }

    /**
     * Конструктор для создания экземпляра {@code TransactionController} с определенным сервисом {@code PlayerService}.
     *
     * @param playerService сервис для работы с сущностями "Player".
     */
    public TransactionController(PlayerService playerService) {
        this.playerService = playerService;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Обрабатывает POST-запросы.
     * Дебетует счет игрока на указанную сумму и возвращает ответ с результатом операции.
     *
     * @param req  объект HTTP-запроса
     * @param resp объект HTTP-ответа
     * @throws ServletException если происходит ошибка сервлета
     * @throws IOException      если происходит ошибка ввода или вывода
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = readJsonFromRequest(req);
        ObjectMapper objectMapper = new ObjectMapper();
        TransactionDto transactionDto = objectMapper.readValue(json, TransactionDto.class);

        try {
            playerService.debit(transactionDto.getUsername(), transactionDto.getId(), transactionDto.getAmount());
            resp.setStatus(HttpServletResponse.SC_CREATED);
            Map<String, String> response = new HashMap<>();
            response.put("success", "Debit operation successful");
            String jsonResponse = objectMapper.writeValueAsString(response);
            resp.getWriter().write(jsonResponse);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            String jsonResponse = objectMapper.writeValueAsString(response);
            resp.getWriter().write(jsonResponse);
        }
    }

    /**
     * Читает JSON из запроса и возвращает его в виде строкового значения.
     *
     * @param req объект HTTP-запроса
     * @return строковое представление JSON из запроса
     * @throws IOException если происходит ошибка ввода или вывода
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
     * Обрабатывает PUT-запросы.
     * Зачисляет счет игрока на указанную сумму и возвращает ответ с результатом операции.
     *
     * @param req  объект HTTP-запроса
     * @param resp объект HTTP-ответа
     * @throws ServletException если происходит ошибка сервлета
     * @throws IOException      если происходит ошибка ввода или вывода
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = readJsonFromRequest(req);
        ObjectMapper objectMapper = new ObjectMapper();
        TransactionDto transactionDto = objectMapper.readValue(json, TransactionDto.class);

        try {
            playerService.credit(transactionDto.getUsername(), transactionDto.getId(), transactionDto.getAmount());
            resp.setStatus(HttpServletResponse.SC_OK);
            Map<String, String> response = new HashMap<>();
            response.put("success", "Credit operation successful");
            String jsonResponse = objectMapper.writeValueAsString(response);
            resp.getWriter().write(jsonResponse);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            String jsonResponse = objectMapper.writeValueAsString(response);
            resp.getWriter().write(jsonResponse);
        }
    }


    /**
     * Обрабатывает GET-запросы.
     * Получает историю транзакций игрока и возвращает в ответе список транзакций в формате JSON.
     *
     * @param req  объект HTTP-запроса
     * @param resp объект HTTP-ответа
     * @throws ServletException если происходит ошибка сервлета
     * @throws IOException      если происходит ошибка ввода или вывода
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        try {
            List<Transaction> transactionList = playerService.getTransactionHistory(username);
            String jsonResponse = objectMapper.writeValueAsString(transactionList);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonResponse);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            String jsonResponse = objectMapper.writeValueAsString(response);
            resp.getWriter().write(jsonResponse);
        }
    }


    /**
     * Выполняет дебитовую операцию по указанному имени пользователя, ID транзакции и сумме.
     *
     * @param username      имя пользователя
     * @param transactionId ID транзакции
     * @param amount        сумма операции
     * @throws Exception если происходит ошибка в ходе выполнения операции
     */

    public void debitTransaction(String username, String transactionId, double amount) throws Exception {
        try {
            playerService.debit(username, transactionId, amount);
            System.out.println("Дебетовая транзакция прошла успешно");
        } catch (Exception e) {
            System.out.println("Дебетовая транзакция не удалась: " + e.getMessage());
        }
    }


    /**
     * Выполняет кредитовую операцию по указанному имени пользователя, ID транзакции и сумме.
     *
     * @param username      имя пользователя
     * @param transactionId ID транзакции
     * @param amount        сумма операции
     * @throws Exception если происходит ошибка в ходе выполнения операции
     */
    public void creditTransaction(String username, String transactionId, double amount) throws Exception {
        try {
            playerService.credit(username, transactionId, amount);
            System.out.println("Кредитная транзакция прошла успешно --> $");
        } catch (Exception e) {
            System.out.println("Кредитная транзакция не удалась ¯\\_(ツ)_/¯ : " + e.getMessage());
        }
    }

    /**
     * Возвращает список всех транзакций по указанному имени пользователя.
     *
     * @param username имя пользователя
     * @return список транзакций пользователя
     */
    public List<Transaction> getTransactionHistory(String username) {
        return playerService.getTransactionHistory(username);
    }

}