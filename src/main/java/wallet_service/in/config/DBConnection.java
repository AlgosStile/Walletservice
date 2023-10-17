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
        createTablesIfNotExist();
        createSequenceIfNotExist();
    }


    public static synchronized DBConnection getInstance() throws SQLException {
        if (instance == null) {
            try {
                instance = new DBConnection();
            } catch (SQLException e) {
                System.out.println("Ошибка при создании соединения с базой данных: " + e.getMessage());
                throw e;
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private final String sequenceQuery = "CREATE SEQUENCE IF NOT EXISTS wallet.transaction_id_seq;";

    private void createSequenceIfNotExist() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sequenceQuery);
        }
    }


    public void createTablesIfNotExist() throws SQLException {
        try (
                PreparedStatement stmt1 = connection.prepareStatement("CREATE SCHEMA IF NOT EXISTS wallet;");
                PreparedStatement stmt2 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS wallet.players (id SERIAL PRIMARY KEY, username VARCHAR(50), password VARCHAR(50), balance DOUBLE PRECISION)");
                PreparedStatement stmt3 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS wallet.transactions (id INTEGER PRIMARY KEY DEFAULT nextval('wallet.transaction_id_seq'), username VARCHAR(50), amount DOUBLE PRECISION, type VARCHAR(20), balance DOUBLE PRECISION)");
                PreparedStatement stmt4 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS wallet.actions (id SERIAL PRIMARY KEY, username VARCHAR(50), action VARCHAR(50), detail TEXT)")
        ) {
            stmt1.execute();
            stmt2.execute();
            stmt3.execute();
            stmt4.execute();
        }
    }

}

