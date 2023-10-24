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

/**
 * Сервлет, обрабатывающий запросы на получение истории транзакций игрока.
 * Отображает все транзакции, связанные с указанным именем пользователя в формате JSON.
 *
 * @author Олег Тодор
 * @see HttpServlet
 */
@WebServlet({"/transaction/history"})
public class TransactionHistoryController extends HttpServlet {
    /**
     * Сервис для работы с данными игроков.
     */
    private PlayerService playerService;

    /**
     * Создает новый экземпляр TransactionHistoryController.
     *
     * @param playerService сервис для работы с данными игроков
     * @see PlayerService
     */
    public TransactionHistoryController(PlayerService playerService) {
        this.playerService = new PlayerServiceImpl();
    }

    /**
     * Обрабатывает GET запрос на URL "/transaction/history". Получает список транзакций указанного пользователя.
     * Преобразует эти транзакции в DTO и конвертирует их в формат JSON, который затем возвращается в ответе.
     *
     * Параметр HTTP запроса "username" указывает имя пользователя, для которого необходимо показать историю транзакций.
     *
     * @param req  HTTP запрос от клиента
     * @param resp HTTP ответ сервера
     * @throws IOException      если во время обработки запроса возникает ошибка ввода/вывода
     * @throws ServletException если при обработке GET запроса возникает ошибка
     */
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
