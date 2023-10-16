package wallet_service.in.repository;

import wallet_service.in.config.DBConnection;
import wallet_service.in.model.Player;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;



public class PlayerRepository {
    private Map<String, Player> players; // Коллекция игроков


    public PlayerRepository() {
        this.players = new ConcurrentHashMap<>();

        try (Connection connection = DBConnection.getInstance().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS player (username TEXT PRIMARY KEY, password TEXT, balance NUMERIC)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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