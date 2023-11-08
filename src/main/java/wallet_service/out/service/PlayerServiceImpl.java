package main.java.wallet_service.out.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.java.wallet_service.out.model.Action;
import main.java.wallet_service.out.model.Player;
import main.java.wallet_service.out.model.Transaction;
import main.java.wallet_service.out.repository.ActionRepository;
import main.java.wallet_service.out.repository.PlayerRepository;
import main.java.wallet_service.out.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Класс PlayerServiceImpl представляет сервис для управления данными игроков,
 * включая действия, транзакции и профили.
 *
 * @Author Олег Тодор
 */
@Service
public class PlayerServiceImpl {
    private final ActionRepository actionRepository;
    private final TransactionRepository transactionRepository;
    private final PlayerRepository playerRepository;

    /**
     * Конструктор класса PlayerServiceImpl.
     *
     * @param playerRepository      репозиторий для управления профилями игроков
     * @param actionRepository      репозиторий для управления действиями игроков
     * @param transactionRepository репозиторий для управления транзакциями игроков
     */
    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, ActionRepository actionRepository,
                             TransactionRepository transactionRepository) {
        this.playerRepository = playerRepository;
        this.actionRepository = actionRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Возвращает игрока по указанному имени пользователя.
     *
     * @param username имя пользователя
     * @return объект игрока
     */
    public Player getPlayer(String username) {
        return (Player) playerRepository.findByUsername(username);
    }

    /**
     * Сохраняет игрока в базе данных.
     *
     * @param player объект игрока, который нужно сохранить
     */
    public void savePlayer(Player player) {
        playerRepository.savePlayer(player);
    }

    /**
     * Выполняет дебетовую транзакцию для указанного пользователя.
     *
     * @param username имя пользователя
     * @param amount   сумма транзакции
     */
    public void debitTransaction(String username, BigDecimal amount) {
        Player player = (Player) playerRepository.findByUsername(username);
        BigDecimal newBalance = BigDecimal.valueOf(player.getBalance()).subtract(amount);
        player.setBalance(newBalance.intValue());
        playerRepository.savePlayer(player);
    }

    /**
     * Выполняет кредитовую транзакцию для указанного пользователя.
     *
     * @param username имя пользователя
     * @param amount   сумма транзакции
     */
    public void creditTransaction(String username, BigDecimal amount) {
        Player player = (Player) playerRepository.findByUsername(username);
        BigDecimal newBalance = BigDecimal.valueOf(player.getBalance()).add(amount);
        player.setBalance(newBalance.intValue());
        playerRepository.savePlayer(player);
    }

    /**
     * Возвращает список действий игрока по указанному имени пользователя.
     *
     * @param username имя пользователя
     * @return список действий игрока
     */
    public List<Action> getPlayerActions(String username) {
        return (List<Action>) actionRepository.findByUsername(username);
    }

    /**
     * Возвращает историю транзакций игрока по указанному имени пользователя.
     *
     * @param username имя пользователя
     * @return список транзакций игрока
     */
    public List<Transaction> getTransactionHistory(String username) {
        return (List<Transaction>) transactionRepository.findByPlayerUsername(username);
    }

    /**
     * Выполняет дебетовую операцию для указанного пользователя с использованием идентификатора.
     *
     * @param username имя пользователя
     * @param id       идентификатор операции
     * @param amount   сумма операции
     */
    public void debit(String username, int id, double amount) {
        Player player = (Player) playerRepository.findByUsername(username);
        BigDecimal newBalance = BigDecimal.valueOf(player.getBalance()).subtract(BigDecimal.valueOf(amount));
        player.setBalance(newBalance.intValue());
        playerRepository.savePlayer(player);
    }

    /**
     * Выполняет кредитовую операцию для указанного пользователя с использованием идентификатора.
     *
     * @param username имя пользователя
     * @param id       идентификатор операции
     * @param amount   сумма операции
     */
    public void credit(String username, int id, double amount) {
        Player player = (Player) playerRepository.findByUsername(username);
        BigDecimal newBalance = BigDecimal.valueOf(player.getBalance()).add(BigDecimal.valueOf(amount));
        player.setBalance(newBalance.intValue());
        playerRepository.savePlayer(player);
    }
}
