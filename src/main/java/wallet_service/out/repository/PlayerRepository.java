package wallet_service.in.repository;

import wallet_service.in.model.Player;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Репозиторий игроков.
 *
 * Этот класс представляет репозиторий игроков, который хранит информацию об игроках в системе.
 * Позволяет добавлять, получать, удалять и обновлять данные об игроках.
 *
 * @author Олег Тодор
 */
public class PlayerRepository {
    private Map<String, Player> players;

    /**
     * Создает экземпляр класса PlayerRepository и инициализирует коллекцию игроков.
     */
    public PlayerRepository() {
        this.players = new ConcurrentHashMap<>();
    }

    /**
     * Добавляет игрока в репозиторий.
     *
     * @param player Игрок для добавления.
     */
    public void addPlayer(Player player) {
        players.put(player.getUsername(), player);
    }
    /**
     * Возвращает игрока по указанному имени пользователя.
     *
     * @param username Имя пользователя.
     * @return Игрок, соответствующий указанному имени пользователя, или null, если игрок не найден.
     */

    public Player getPlayer(String username) {

        return players.get(username);
    }

    /**
     * Возвращает коллекцию всех игроков в репозитории.
     *
     * @return Коллекция игроков.
     */
    public Collection<Player> getAllPlayers() {
        return players.values();
    }
    /**
     * Удаляет игрока из репозитория по указанному имени пользователя.
     *
     * @param username Имя пользователя игрока, которого нужно удалить.
     */
    public void removePlayer(String username) {
        players.remove(username);
    }
    /**
     * Обновляет данные игрока с указанным именем пользователя.
     * @param username Имя пользователя игрока, данные которого нужно обновить.
     * @param password Новый пароль игрока.
     */
    public void updatePlayer(String username, String password) {
    }
}