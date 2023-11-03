package wallet_service.out.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wallet_service.out.model.Transaction;
import wallet_service.out.repository.PlayerRepository;
import wallet_service.out.repository.TransactionRepository;
import wallet_service.out.service.PlayerServiceImpl;

import java.util.List;

@RestController
public class TransactionController {
    private final PlayerServiceImpl playerServiceImpl;
    private final TransactionRepository transactionRepository;
    public TransactionController(PlayerServiceImpl playerServiceImpl, PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerServiceImpl = playerServiceImpl;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/debit")
    public ResponseEntity<String> debitTransaction(@RequestParam String username, @RequestParam int id, @RequestParam double amount) {
        try {
            playerServiceImpl.debit(username, id, amount);
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Дебетовая транзакция успешно выполнена!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при выполнении дебетовой транзакции: " + e.getMessage());
        }
    }

    @PostMapping("/credit")
    public ResponseEntity<String> creditTransaction(@RequestParam String username, @RequestParam int id, @RequestParam double amount) {
        try {
            playerServiceImpl.credit(username, id, amount);
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Кредитная транзакция успешно выполнена!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при выполнении кредитной транзакции: " + e.getMessage());
        }
    }

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