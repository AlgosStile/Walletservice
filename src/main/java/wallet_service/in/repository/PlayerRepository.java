package wallet_service.in.repository;

import wallet_service.in.config.DBConnection;
import wallet_service.in.model.Player;

import java.sql.*;
import java.sql.PreparedStatement;

public class PlayerRepository {
    private static PlayerRepository instance;

    private static final String INSERT_SQL = "INSERT INTO wallet.players(username, password, balance) VALUES (?, ?, ?)";
    private static final String SELECT_SQL = "SELECT * FROM wallet.players WHERE username = ?";
    private static final String UPDATE_SQL = "UPDATE wallet.players SET balance = ? WHERE username = ?";
    private static final String DELETE_SQL = "DELETE FROM wallet.players WHERE username = ?";


    private final Connection connection;

    public PlayerRepository() throws SQLException {
        connection = DBConnection.getInstance().getConnection();
    }

    public static synchronized PlayerRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new PlayerRepository();
        }
        return instance;
    }

    public int addPlayer(Player player) throws SQLException {
        int newPlayerId = 0;
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, player.getUsername());
            preparedStatement.setString(2, player.getPassword());
            preparedStatement.setDouble(3, player.getBalance());
            preparedStatement.executeUpdate();

            PreparedStatement sequenceStatement = connection.prepareStatement("SELECT nextval('wallet.transaction_id_seq')");
            ResultSet rsSequence = sequenceStatement.executeQuery();
            if (rsSequence.next()) {
                int transactionId = rsSequence.getInt(1);
                player.setId(transactionId);
            }
            rsSequence.close();
            sequenceStatement.close();

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
                    connection.rollback();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } finally {
            connection.setAutoCommit(true);
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
    public double getBalance(String username) throws SQLException {
        String sql = "SELECT balance FROM wallet.players WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            } else {
                throw new RuntimeException("Пользователь " + username + " не найден.");
            }
        }
    }

}

