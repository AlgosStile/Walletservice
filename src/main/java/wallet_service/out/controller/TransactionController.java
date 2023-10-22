package wallet_service.out.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import wallet_service.in.model.Transaction;
import wallet_service.out.service.PlayerService;

import java.util.List;
import wallet_service.out.dto.PlayerDto;
import wallet_service.out.dto.TransactionDto;
import wallet_service.out.service.PlayerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TransactionController extends HttpServlet {

    private final PlayerService playerService;
    private ObjectMapper objectMapper;

    public TransactionController(PlayerService playerService) {
        this.playerService = playerService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String transactionId = req.getParameter("transactionId");
        double amount = Double.parseDouble(req.getParameter("amount"));

        try {
            playerService.credit(username, transactionId, amount);
            resp.setStatus(HttpServletResponse.SC_CREATED);
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String transactionId = req.getParameter("transactionId");
        double amount = Double.parseDouble(req.getParameter("amount"));

        try {
            playerService.debit(username, transactionId, amount);
            resp.setStatus(HttpServletResponse.SC_OK);
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



    public void debitTransaction(String username, String transactionId, double amount) throws Exception {
        try {
            playerService.debit(username, transactionId, amount);
            System.out.println("Дебетовая транзакция прошла успешно");
        } catch (Exception e) {
            System.out.println("Дебетовая транзакция не удалась: " + e.getMessage());
        }
    }


    public void creditTransaction(String username, String transactionId, double amount) throws Exception {
        try {
            playerService.credit(username, transactionId, amount);
            System.out.println("Кредитная транзакция прошла успешно --> $");
        } catch (Exception e) {
            System.out.println("Кредитная транзакция не удалась ¯\\_(ツ)_/¯ : " + e.getMessage());
        }
    }


    public List<Transaction> getTransactionHistory(String username) {
        return playerService.getTransactionHistory(username);
    }

}