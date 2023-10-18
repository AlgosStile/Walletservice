package wallet_service.in.config;

import java.sql.*;



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
//        createTablesIfNotExist();
//        createSequenceIfNotExist();
    }


    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            int attempts = 0;
            while (attempts < 3) {
                try {
                    instance = new DBConnection();
                } catch (SQLException e) {
                    attempts++;
                    System.out.println("Не удалось подключиться к базе данных, попытка номер " + attempts);
                    try {
                        Thread.sleep(2000); // Подождите 2 секунды перед следующей попыткой.
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    continue;
                }
                break;
            }
            if (instance == null) {
                throw new RuntimeException("Не удалось подключиться к базе данных.");
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }



}

