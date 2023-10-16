package wallet_service.in.repository;

import wallet_service.in.config.DBConnection;
import wallet_service.in.model.Player;

import java.sql.*;

public class PlayerRepository {

    private static final String INSERT_SQL = "INSERT INTO players(username, password, balance) VALUES (?, ?, ?)";
    private static final String SELECT_SQL = "SELECT * FROM players WHERE username = ?";
    private static final String UPDATE_SQL = "UPDATE players SET balance = ? WHERE username = ?";
    private static final String DELETE_SQL = "DELETE FROM players WHERE username = ?";

    private Connection connection;

    public PlayerRepository() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
    }

    public int addPlayer(Player player) throws SQLException {
        int newPlayerId = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, player.getUsername());
            preparedStatement.setString(2, player.getPassword());
            preparedStatement.setDouble(3, player.getBalance());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                newPlayerId = rs.getInt(1);
            }

            preparedStatement.close();
            connection.commit();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            if (connection != null) {
                try {
                    System.out.println("Транзакция отменена");
                    connection.rollback();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return newPlayerId;
    }
    public Player getPlayer(String username) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {
            preparedStatement.setString(1, username);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new Player(rs.getString("username"),
                            rs.getString("password"));
                }
            }
        }
        return null;
    }

    public void updatePlayer(String username, double balance) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setDouble(1, balance);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        }
    }

    public void removePlayer(String username) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        }
    }
}