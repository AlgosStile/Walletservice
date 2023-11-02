package wallet_service.out.controller;

import org.springframework.web.bind.annotation.*;
import wallet_service.out.model.Action;
import wallet_service.out.model.Player;
import wallet_service.out.service.PlayerServiceImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Класс PlayerController представляет контроллер для обработки запросов, связанных с игроками.
 * Он предоставляет REST-эндпоинты для получения информации о игроках, сохранения игроков, проведения дебетовых и кредитовых транзакций,
 * а также получения списка действий игрока.
 */
@RestController
@RequestMapping("/player")
public class PlayerController {
    private final PlayerServiceImpl playerServiceImpl;

    /**
     * Конструктор класса PlayerController.
     *
     * @param playerServiceImpl экземпляр PlayerServiceImpl, который предоставляет реализацию методов для работы с игроками.
     */
    public PlayerController(PlayerServiceImpl playerServiceImpl) {
        this.playerServiceImpl = playerServiceImpl;
    }

    /**
     * Метод getPlayer выполняет обработку GET-запроса для получения информации о игроке по его имени пользователя.
     *
     * @param username имя пользователя игрока.
     * @return объект Player, содержащий информацию о игроке.
     */
    @GetMapping("/{username}")
    public Player getPlayer(@PathVariable String username) {
        return playerServiceImpl.getPlayer(username);
    }


    /**
     * Метод savePlayer выполняет обработку POST-запроса для сохранения информации о новом игроке.
     *
     * @param player объект Player, содержащий информацию о новом игроке.
     */
    @PostMapping("/")
    public void savePlayer(@RequestBody Player player) {
        playerServiceImpl.savePlayer(player);
    }

    /**
     * Метод debitTransaction выполняет обработку POST-запроса для проведения дебетовой транзакции для игрока с заданным именем пользователя.
     *
     * @param username имя пользователя игрока.
     * @param amount   сумма дебетовой транзакции.
     */
    @PostMapping("/{username}/debit")
    public void debitTransaction(@PathVariable String username, @RequestParam BigDecimal amount) {
        playerServiceImpl.debitTransaction(username, amount);
    }

    /**
     * Метод creditTransaction выполняет обработку POST-запроса для проведения кредитовой транзакции для игрока с заданным именем пользователя.
     *
     * @param username имя пользователя игрока.
     * @param amount   сумма кредитовой транзакции.
     */
    @PostMapping("/{username}/credit")
    public void creditTransaction(@PathVariable String username, @RequestParam BigDecimal amount) {
        playerServiceImpl.creditTransaction(username, amount);
    }

    /**
     * Метод getPlayerActions выполняет обработку GET-запроса для получения списка действий игрока с заданным именем пользователя.
     *
     * @param username имя пользователя игрока.
     * @return список объектов Action, представляющих действия игрока.
     */
    @GetMapping("/{username}/actions")
    public List<Action> getPlayerActions(@PathVariable String username) {
        return playerServiceImpl.getPlayerActions(username);
    }
}