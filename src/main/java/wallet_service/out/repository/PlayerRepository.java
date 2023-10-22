package wallet_service.out.repository;

import wallet_service.in.model.Player;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class PlayerRepository {
    private Map<String, Player> players; // Коллекция игроков


    public PlayerRepository() {
        this.players = new ConcurrentHashMap<>();
    }


    public void addPlayer(Player player) {
        players.put(player.getUsername(), player);
    }


    public Player getPlayer(String username) {
        return players.get(username);
    }


    public Collection<Player> getAllPlayers() {
        return players.values();
    }

    public void removePlayer(String username) {
        players.remove(username);
    }
}