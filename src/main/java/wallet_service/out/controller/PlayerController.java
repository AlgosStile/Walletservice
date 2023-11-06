package wallet_service.out.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wallet_service.out.model.Action;
import wallet_service.out.model.Player;
import wallet_service.out.repository.PlayerRepository;
import wallet_service.out.service.PlayerServiceImpl;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class PlayerController {

    private final PlayerRepository playerRepository;
    private final PlayerServiceImpl playerServiceImpl;
    @Autowired
    public PlayerController(PlayerRepository playerRepository, PlayerServiceImpl playerServiceImpl) {
        this.playerServiceImpl = playerServiceImpl;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/{username}")
    public Player getPlayer(@PathVariable String username) {
        return playerRepository.findByUsername(username);
    }

    @PostMapping("/")
    public void savePlayer(@RequestBody Player player) {
        playerRepository.savePlayer(player);
    }

    @PostMapping("/{username}/debit")
    public void debitTransaction(@PathVariable String username, @RequestParam BigDecimal amount) {
        playerServiceImpl.debitTransaction(username, amount);
    }

    @PostMapping("/{username}/credit")
    public void creditTransaction(@PathVariable String username, @RequestParam BigDecimal amount) {
        playerServiceImpl.creditTransaction(username, amount);
    }

    @GetMapping("/{username}/actions")
    public List<Action> getPlayerActions(@PathVariable String username) {
        return playerServiceImpl.getPlayerActions(username);
    }
}