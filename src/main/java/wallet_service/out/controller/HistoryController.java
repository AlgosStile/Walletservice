package wallet_service.out.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import wallet_service.in.model.Action;
import wallet_service.out.service.PlayerService;
import wallet_service.out.service.PlayerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс-контроллер, отвечающий за обработку HTTP-запросов, связанных с получением истории действий игрока.
 *
 * <p>Данный контроллер обрабатывает HTTP GET-запросы, направленные на путь "/player/history". Он позволяет получить
 * историю действий игрока на основе его имени пользователя.
 *
 * @author Олег Тодор
 * @see HttpServlet
 */
@WebServlet({"/player/history"})
public class HistoryController extends HttpServlet {
    private ObjectMapper objectMapper;
    private PlayerService playerService;

    /**
     * Создает экземпляр класса `HistoryController` без параметров.
     * Используется для соответствия настроек в web.xml.
     */
    public HistoryController() {
    }

    /**
     * Создает экземпляр класса `HistoryController` с указанным сервисом игрока.
     *
     * @param playerService Сервис игрока, используемый для получения истории действий игрока.
     */
    public HistoryController(PlayerService playerService) {
        this.playerService = playerService;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Инициализация контроллера.
     * Создает экземпляры ObjectMapper и PlayerService.
     * Вызывается контейнером сервлетов при инициализации сервлета.
     */
    public void init() {
        this.playerService = new PlayerServiceImpl();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Обрабатывает HTTP GET-запросы, направленные на путь "/player/history".
     * Возвращает историю действий игрока в формате JSON.
     *
     * @param req  Объект HttpServletRequest, представляющий HTTP-запрос.
     * @param resp Объект HttpServletResponse, представляющий HTTP-ответ.
     * @throws ServletException Если при обработке запроса происходит ошибка сервлета.
     * @throws IOException      Если при чтении или записи данных происходят ошибки ввода-вывода.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        String username = req.getParameter("username");

        try {
            List<Action> history = playerService.getPlayerActions(username);

            String jsonResponse = objectMapper.writeValueAsString(history);
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
}