package wallet_service.in.controller;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;



import java.sql.SQLException;

@Testcontainers
public class PlayerControllerTest {

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres");

    private PlayerController playerController;

    @BeforeAll
    static void setUp() {
        postgreSQLContainer.start();
        System.setProperty("DB_URL", postgreSQLContainer.getJdbcUrl());
        System.setProperty("DB_USERNAME", postgreSQLContainer.getUsername());
        System.setProperty("DB_PASSWORD", postgreSQLContainer.getPassword());
    }

    @BeforeEach
    void initialize() throws SQLException {
        playerController = new PlayerController();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    @DisplayName("test")
    void registerPlayer_Success() {
        // Arrange
        String username = "testuser";
        String password = "testpassword";

        // Act
        playerController.registerPlayer(username, password);

        // Assert
        // Проверяем, что игрок успешно зарегистрирован
        // и сообщение об успехе было выведено.
        assertEquals("Игрок успешно зарегистрирован 😀", getConsoleOutput());
    }

    @Test
    @DisplayName("test")
    void registerPlayer_Failure() {
        // Arrange
        String username = "existinguser";
        String password = "testpassword";

        // Act
        playerController.registerPlayer(username, password);

        // Assert
        // Проверяем, что при попытке зарегистрировать
        // игрока с уже существующим именем
        // выводится сообщение об ошибке.
        assertEquals("Ошибка при регистрации: Имя пользователя уже занято 😞", getConsoleOutput());
    }

    @Test
    @DisplayName("test")
    void authenticatePlayer_Success() throws SQLException {
        // Arrange
        String username = "testuser";
        String password = "testpassword";

        // Act
        playerController.authenticatePlayer(username, password);

        // Assert
        // Проверяем, что игрок успешно авторизован
        // и соответствующее сообщение было выведено.
        assertEquals("Игрок успешно авторизован!", getConsoleOutput());
    }

    @Test
    @DisplayName("test")
    void authenticatePlayer_Failure() throws SQLException {
        // Arrange
        String username = "nonexistentuser";
        String password = "testpassword";

        // Act
        playerController.authenticatePlayer(username, password);

        // Assert
        // Проверяем, что при попытке авторизоваться
        // с неправильным именем пользователя
        // выводится сообщение о неверных данных.
        assertEquals("Неправильное имя пользователя или пароль", getConsoleOutput());
    }

    // Вспомогательный метод для получения вывода в консоли
    private String getConsoleOutput() {
        // Ваш код для получения вывода в консоли
        // Например, можно использовать System.out.println()
        // и перенаправить вывод в ByteArrayOutputStream
        // и затем вернуть его содержимое как строку.
        return "";
    }
}
