package main.java.org.wallet_service.out.service;

import main.java.org.wallet_service.out.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.java.org.wallet_service.out.model.Action;
import main.java.org.wallet_service.out.model.Player;
import main.java.org.wallet_service.out.model.Transaction;
import main.java.org.wallet_service.out.repository.PlayerRepository;
import main.java.org.wallet_service.out.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PlayerServiceImpl {
    private final ActionRepository actionRepository;
    private final TransactionRepository transactionRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, ActionRepository actionRepository, TransactionRepository transactionRepository) {
        this.playerRepository = playerRepository;
        this.actionRepository = actionRepository;
        this.transactionRepository = transactionRepository;
    }

    public Player getPlayer(String username) {
        return playerRepository.findByUsername(username);
    }

    public void savePlayer(Player player) {
        playerRepository.savePlayer(player);
    }

    public void debitTransaction(String username, BigDecimal amount) {
        Player player = playerRepository.findByUsername(username);
        BigDecimal newBalance = BigDecimal.valueOf(player.getBalance()).subtract(amount);
        player.setBalance(newBalance.intValue());
        playerRepository.savePlayer(player);

        Transaction transaction = new Transaction();
        transaction.setPlayer(player);
        transaction.setAmount(amount.doubleValue());
        transaction.setType("debit");
        transaction.setBalance(player.getBalance());
        transactionRepository.saveTransaction(transaction);

    }

    public void creditTransaction(String username, BigDecimal amount) {
        Player player = playerRepository.findByUsername(username);
        BigDecimal newBalance = BigDecimal.valueOf(player.getBalance()).add(amount);
        player.setBalance(newBalance.intValue());
        playerRepository.savePlayer(player);

        Transaction transaction = new Transaction();
        transaction.setPlayer(player);
        transaction.setAmount(amount.doubleValue());
        transactionRepository.saveTransaction(transaction);
    }

    public List<Action> getPlayerActions(String username) {
        return actionRepository.findByUsername(username);
    }

    public List<Transaction> getTransactionHistory(String username) {
        return transactionRepository.findByPlayerUsername(username);
    }

    public void debit(String username, int id, double amount) {
        Player player = playerRepository.findByUsername(username);
        BigDecimal newBalance = BigDecimal.valueOf(player.getBalance()).subtract(BigDecimal.valueOf(amount));
        player.setBalance(newBalance.intValue());
        playerRepository.savePlayer(player);
    }

    public void credit(String username, int id, double amount) {
        Player player = playerRepository.findByUsername(username);
        BigDecimal newBalance = BigDecimal.valueOf(player.getBalance()).add(BigDecimal.valueOf(amount));
        player.setBalance(newBalance.intValue());
        playerRepository.savePlayer(player);

    }

    public Player findByUsername(String username) {
        return playerRepository.findByUsername(username);
    }
}