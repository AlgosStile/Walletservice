package wallet_service.in.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static DBConnection instance;
    private final Connection connection;
    private final String url;
    private final String username;
    private final String password;

    private DBConnection() throws SQLException {
        Config config = Config.getInstance();
        url = config.getProperty("db.url");
        username = config.getProperty("db.user");
        password = config.getProperty("db.password");
        connection = DriverManager.getConnection(url, username, password);
        createTablesIfNotExist();
    }


    public static synchronized DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void createTablesIfNotExist() throws SQLException {
        try (Statement stmt = connection.createStatement()) {

            stmt.execute(
                    "CREATE SCHEMA IF NOT EXISTS wallet;"
            );
            stmt.execute(
                    "CREATE SEQUENCE IF NOT EXISTS wallet.transaction_id_seq;"
            );
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS wallet.players (" +
                            "id SERIAL PRIMARY KEY, " +
                            "username VARCHAR(50), " +
                            "password VARCHAR(50), " +
                            "balance DOUBLE PRECISION)"
            );
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS wallet.transactions (" +
                            "id VARCHAR PRIMARY KEY DEFAULT nextval('wallet.transaction_id_seq'), " +
                            "username VARCHAR(50), " +
                            "amount DOUBLE PRECISION, " +
                            "type VARCHAR(20), " +
                            "balance DOUBLE PRECISION)"
            );
        }
    }
}