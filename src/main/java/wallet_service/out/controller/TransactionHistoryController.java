package wallet_service.out.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import wallet_service.in.model.Transaction;
import wallet_service.out.dto.TransactionDto;
import wallet_service.out.mapper.TransactionMapper;
import wallet_service.out.service.PlayerService;
import wallet_service.out.service.PlayerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet({"/transaction/history"})
public class TransactionHistoryController extends HttpServlet {
    private PlayerService playerService;

    public TransactionHistoryController(PlayerService playerService) {
        this.playerService = new PlayerServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        List<Transaction> transactions = playerService.getTransactionHistory(username);


        List<TransactionDto> transactionDtos = transactions.stream()
                .map(TransactionMapper.INSTANCE::transactionToTransactionDto)
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        String transactionsJson = objectMapper.writeValueAsString(transactionDtos);

        resp.setContentType("application/json");
        resp.getWriter().print(transactionsJson);
    }
}
