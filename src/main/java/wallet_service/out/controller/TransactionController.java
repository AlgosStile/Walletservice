package wallet_service.out.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wallet_service.out.model.Transaction;
import wallet_service.out.repository.PlayerRepository;
import wallet_service.out.repository.TransactionRepository;
import wallet_service.out.service.PlayerServiceImpl;

import java.util.List;

/**
 * Класс TransactionController является Spring MVC контроллером, который обрабатывает HTTP-запросы, связанные с транзакциями.
 */
@RestController
public class TransactionController {
    private final PlayerServiceImpl playerServiceImpl;
    private final TransactionRepository transactionRepository;
    /**
     * Конструктор для создания нового экземпляра TransactionController с указанными зависимостями.
     *
     * @param playerServiceImpl       Зависимость PlayerServiceImpl.
     * @param transactionRepository   Зависимость TransactionRepository.
     */
    public TransactionController(PlayerServiceImpl playerServiceImpl, PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerServiceImpl = playerServiceImpl;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Обрабатывает POST-запрос для выполнения дебетовой транзакции.
     *
     * @param username  Имя пользователя.
     * @param id        Идентификатор транзакции.
     * @param amount    Сумма транзакции.
     * @return ResponseEntity<String>   Если дебетовая транзакция успешно выполнена, возвращает ResponseEntity с HTTP-статусом 200 (OK) и сообщением об успешном выполнении.
     * Если произошла ошибка, возвращает ResponseEntity с HTTP-статусом 400 (Bad Request) и сообщением об ошибке.
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
     * Обрабатывает POST-запрос для выполнения кредитной транзакции.
     *
     * @param username  Имя пользователя.
     * @param id        Идентификатор транзакции.
     * @param amount    Сумма транзакции.
     * @return ResponseEntity<String>   Если кредитная транзакция успешно выполнена, возвращает ResponseEntity с HTTP-статусом 200 (OK) и сообщением об успешном выполнении.
     * Если произошла ошибка, возвращает ResponseEntity с HTTP-статусом 400 (Bad Request) и сообщением об ошибке.
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
     * Обрабатывает GET-запрос для получения истории транзакций для определенного игрока.
     *
     * @param username  Имя пользователя.
     * @return ResponseEntity<List<Transaction>>   Если получение истории транзакций успешно, возвращает ResponseEntity с HTTP-статусом 200 (OK) и списком транзакций.
     * Если произошла ошибка, возвращает ResponseEntity с HTTP-статусом 400 (Bad Request) и пустым телом.
     */
    @GetMapping("/transactions/{username}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable String username) {
        try {
            List<Transaction> transactions = playerServiceImpl.getTransactionHistory(username);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}