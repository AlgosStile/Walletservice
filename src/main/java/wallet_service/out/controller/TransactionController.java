package wallet_service.out.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wallet_service.out.model.Transaction;
import wallet_service.out.repository.PlayerRepository;
import wallet_service.out.repository.TransactionRepository;
import wallet_service.out.service.PlayerServiceImpl;

import java.util.List;

/**
 * Класс TransactionController представляет контроллер для работы с транзакциями игроков.
 * Он обрабатывает HTTP-запросы, связанные с транзакциями, и взаимодействует с репозиторием игроков,
 * репозиторием транзакций и сервисом игроков.
 *
 * @author Олег Тодор
 */
@RestController
public class TransactionController {

    private final PlayerRepository playerRepository;
    private final TransactionRepository transactionRepository;
    private final PlayerServiceImpl playerServiceImpl;

    /**
     * Конструктор класса TransactionController.
     *
     * @param playerServiceImpl     сервис игроков
     * @param playerRepository      репозиторий игроков
     * @param transactionRepository репозиторий транзакций
     */
    @Autowired
    public TransactionController(PlayerServiceImpl playerServiceImpl, PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerServiceImpl = playerServiceImpl;
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Обработчик HTTP-запроса POST для выполнения дебетовой транзакции.
     *
     * @param username имя пользователя
     * @param id       идентификатор транзакции
     * @param amount   сумма транзакции
     * @return объект ResponseEntity, содержащий сообщение об успешном выполнении транзакции или об ошибке
     */
    @PostMapping("/debit")
    public ResponseEntity<String> debitTransaction(@RequestParam String username, @RequestParam int id, @RequestParam double amount) {
        try {
            playerServiceImpl.debit(username, id, amount);
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Дебетовая транзакция успешно выполнена!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при выполнении дебетовой транзакции: " + e.getMessage());
        }
    }

    /**
     * Обработчик HTTP-запроса POST для выполнения кредитной транзакции.
     *
     * @param username имя пользователя
     * @param id       идентификатор транзакции
     * @param amount   сумма транзакции
     * @return объект ResponseEntity, содержащий сообщение об успешном выполнении транзакции или об ошибке
     */
    @PostMapping("/credit")
    public ResponseEntity<String> creditTransaction(@RequestParam String username, @RequestParam int id, @RequestParam double amount) {
        try {
            playerServiceImpl.credit(username, id, amount);
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Кредитная транзакция успешно выполнена!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при выполнении кредитной транзакции: " + e.getMessage());
        }
    }

    /**
     * Обработчик HTTP-запроса GET для получения истории транзакций по имени пользователя.
     *
     * @param username имя пользователя
     * @return объект ResponseEntity, содержащий список объектов Transaction
     * с информацией о выполненных транзакциях или ошибку при ее возникновении
     */
    @GetMapping("/transaction/history")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@RequestParam String username) {
        try {
            List<Transaction> transactions = playerServiceImpl.getTransactionHistory(username);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
