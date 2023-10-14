package wallet_service.in.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}