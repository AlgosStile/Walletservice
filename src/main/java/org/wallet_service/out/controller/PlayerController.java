package main.java.org.wallet_service.out.controller;

import main.java.org.wallet_service.out.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import main.java.org.wallet_service.out.model.Action;
import main.java.org.wallet_service.out.model.Player;
import main.java.org.wallet_service.out.repository.PlayerRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Класс PlayerController представляет контроллер для работы с информацией об игроках.
 * Он обрабатывает HTTP-запросы, связанные с игроками, и взаимодействует с репозиторием игроков и сервисом игроков.
 *
 * @author Олег Тодор
 */
@RestController
public class PlayerController {
    private final PlayerRepository playerRepository;
    private final PlayerServiceImpl playerServiceImpl;

    /**
     * Конструктор класса PlayerController.
     *
     * @param playerRepository  репозиторий игроков
     * @param playerServiceImpl сервис игроков
     */
    @Autowired
    public PlayerController(PlayerRepository playerRepository, PlayerServiceImpl playerServiceImpl) {
        this.playerRepository = playerRepository;
        this.playerServiceImpl = playerServiceImpl;
    }

    /**
     * Обработчик HTTP-запроса GET для получения информации об игроке по его имени пользователя.
     *
     * @param username имя пользователя
     * @return объект Player с информацией об игроке
     */
    @GetMapping("/{username}")
    public Player getPlayer(@PathVariable String username) {
        return playerRepository.findByUsername(username);
    }

    /**
     * Обработчик HTTP-запроса POST для сохранения информации об игроке.
     *
     * @param player объект Player с информацией об игроке
     */
    @PostMapping("/")
    public void savePlayer(@RequestBody Player player) {
        playerRepository.savePlayer(player);
    }

    /**
     * Обработчик HTTP-запроса POST для выполнения дебетовой транзакции для указанного пользователя.
     *
     * @param username имя пользователя
     * @param amount   сумма транзакции в BigDecimal
     */
    @PostMapping("/{username}/debit")
    public void debitTransaction(@PathVariable String username, @RequestParam BigDecimal amount) {
        playerServiceImpl.debitTransaction(username, amount);
    }

    /**
     * Обработчик HTTP-запроса POST для выполнения кредитовой транзакции для указанного пользователя.
     *
     * @param username имя пользователя
     * @param amount   сумма транзакции в BigDecimal
     */
    @PostMapping("/{username}/credit")
    public void creditTransaction(@PathVariable String username, @RequestParam BigDecimal amount) {
        playerServiceImpl.creditTransaction(username, amount);
    }

    /**
     * Обработчик HTTP-запроса GET для получения списка действий игрока по его имени пользователя.
     *
     * @param username имя пользователя
     * @return список объектов Action с информацией о действиях игрока
     */
    @GetMapping("/{username}/actions")
    public List<Action> getPlayerActions(@PathVariable String username) {
        return playerServiceImpl.getPlayerActions(username);
    }
}
