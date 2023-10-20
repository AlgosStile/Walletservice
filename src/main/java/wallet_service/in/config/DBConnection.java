package wallet_service.in.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс DBConnection предназначен для осуществления подключения к базе данных.
 * Используется шаблон Singleton для обеспечения единственного экземпляра подключения.
 *
 * @author Олег Тодор
 * @since 2.0.0
 */
public class DBConnection {
    private static DBConnection instance;
    private final Connection connection;
    private final String url;
    private final String username;
    private final String password;

    /**
     * Конструктор класса DBConnection.
     * Создает подключение к базе данных используя параметры конфигурации.
     *
     * @throws SQLException в случае ошибки подключения к базе данных
     */
    private DBConnection() throws SQLException {
        Config config = Config.getInstance();
        url = config.getProperty("db.url");
        username = config.getProperty("db.username");
        password = config.getProperty("db.password");
        connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Метод getInstance обеспечивает доступ к единственному экземпляру класса DBConnection.
     * Если подключение к базе данных отсутствует, метод пытается его создать.
     *
     * @return instance единственный экземпляр класса DBConnection
     * @throws RuntimeException если подключение к базе данных создать не удалось
     */
    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            int attempts = 0;
            while (attempts < 3) {
                try {
                    instance = new DBConnection();
                } catch (SQLException e) {
                    attempts++;
                    System.out.println("Не удалось подключиться к базе данных, попытка номер " + attempts);
                    e.printStackTrace();
                    try {
                        Thread.sleep(2000);
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

    /**
     * Метод getConnection возвращает подключение к базе данных
     *
     * @return Connection подключение к базе данных
     */
    public Connection getConnection() {
        return connection;
    }
}
