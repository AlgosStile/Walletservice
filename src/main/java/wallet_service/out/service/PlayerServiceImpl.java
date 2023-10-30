package wallet_service.out.service;

import org.springframework.stereotype.Service;
import wallet_service.out.model.Action;
import wallet_service.out.model.Player;
import wallet_service.out.model.Transaction;
import wallet_service.out.repository.ActionRepository;
import wallet_service.out.repository.PlayerRepository;
import wallet_service.out.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;


/**
 * Сервис для работы с сущностью Player.
 */
@Service
public class PlayerServiceImpl {
    private final PlayerRepository playerRepository;
    private final ActionRepository actionRepository;
    private final TransactionRepository transactionRepository;

    /**
     * Конструктор класса.
     *
     * @param playerRepository      репозиторий для работы с сущностью Player
     * @param actionRepository      репозиторий для работы с сущностью Action
     * @param transactionRepository репозиторий для работы с сущностью Transaction
     */
    public PlayerServiceImpl(PlayerRepository playerRepository, ActionRepository actionRepository, TransactionRepository transactionRepository) {
        this.playerRepository = playerRepository;
        this.actionRepository = actionRepository;
        this.transactionRepository = transactionRepository;
    }


    /**
     * Возвращает игрока с указанным именем пользователя.
     *
     * @param username имя пользователя
     * @return игрок
     */
    public Player getPlayer(String username) {
        return playerRepository.findByUsername(username);
    }


    /**
     * Сохраняет игрока.
     *
     * @param player игрок
     */
    public void savePlayer(Player player) {
        playerRepository.save(player);
    }


    /**
     * Списывает средства с баланса игрока с указанным именем пользователя.
     *
     * @param username имя пользователя
     * @param amount   сумма списания
     */
    public void debitTransaction(String username, BigDecimal amount) {
        Player player = playerRepository.findByUsername(username);
        BigDecimal newBalance = BigDecimal.valueOf(player.getBalance()).subtract(amount);
        player.setBalance(newBalance.intValue());
        playerRepository.save(player);
    }


    /**
     * Зачисляет средства на баланс игрока с указанным именем пользователя.
     *
     * @param username имя пользователя
     * @param amount   сумма зачисления
     */
    public void creditTransaction(String username, BigDecimal amount) {
        Player player = playerRepository.findByUsername(username);
        BigDecimal newBalance = BigDecimal.valueOf(player.getBalance()).add(amount);
        player.setBalance(newBalance.intValue());
        playerRepository.save(player);
    }


    /**
     * Возвращает список действий игрока с указанным именем пользователя.
     *
     * @param username имя пользователя
     * @return список действий
     */
    public List<Action> getPlayerActions(String username) {
        return actionRepository.findByUsername(username);
    }



    public void debit(String username, int id, double amount) {
    }

    public void credit(String username, int id, double amount) {
    }

    public List<Transaction> getTransactionHistory(String username) {
        return getTransactionHistory(username);
    }
}