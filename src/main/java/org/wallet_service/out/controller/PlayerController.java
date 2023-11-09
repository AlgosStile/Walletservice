package main.java.org.wallet_service.out.controller;

import main.java.org.wallet_service.out.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import main.java.org.wallet_service.out.model.Action;
import main.java.org.wallet_service.out.model.Player;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class PlayerController {
    private final PlayerServiceImpl playerServiceImpl;

    @Autowired
    public PlayerController(PlayerServiceImpl playerServiceImpl) {
        this.playerServiceImpl = playerServiceImpl;
    }

    @GetMapping("/{username}")
    public Player getPlayer(@PathVariable String username) {
        return playerServiceImpl.findByUsername(username);
    }

    @PostMapping("/")
    public void savePlayer(@RequestBody Player player) {
        playerServiceImpl.savePlayer(player);
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
