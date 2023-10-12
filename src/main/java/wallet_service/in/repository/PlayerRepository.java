package wallet_service.in.repository;

import wallet_service.in.model.Player;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс, представляющий репозиторий игроков.
 *
 * @author Олег Тодор
 */
public class PlayerRepository {
    private Map<String, Player> players; // Коллекция игроков

    /**
     * Конструктор класса PlayerRepository.
     */
    public PlayerRepository() {
        this.players = new ConcurrentHashMap<>();
    }

    /**
     * Добавить игрока в репозиторий.
     *
     * @param player Игрок для добавления
     */
    public void addPlayer(Player player) {
        players.put(player.getUsername(), player);
    }

    /**
     * Получить игрока по имени пользователя.
     *
     * @param username Имя пользователя
     * @return Игрок с указанным именем пользователя или null, если игрок не найден
     */
    public Player getPlayer(String username) {
        return players.get(username);
    }

    /**
     * Получить всех игроков.
     *
     * @return Коллекция всех игроков
     */
    public Collection<Player> getAllPlayers() {
        return players.values();
    }
    public void removePlayer(String username) {
        players.remove(username);
    }
}