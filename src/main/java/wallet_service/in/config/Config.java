package wallet_service.in.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Этот класс осуществляет чтение конфигурационных данных из properties-файла.
 * Config предназначен для загрузки и хранения параметров конфигурации, которые могут быть получены по ключу.
 * <p>
 * Это синглтон-класс, что гарантирует глобальное единственное его экземплярное представление в рамках приложения.
 *
 * @author Олег Тодор
 * @since 2.0.0
 */
public class Config {
    private static final Config instance = new Config();
    private final Properties configFile;

    /**
     * Инициализирует новый экземпляр класса Config и загружает конфигурацию.
     */
    private Config() {
        configFile = new Properties();
        loadConfig();
    }

    /**
     * Возвращает единственный экземпляр данного класса, создавая его при первом вызове и сохраняя
     * для всех последующих обращений.
     *
     * @return единственный экземпляр класса Config
     */
    public static Config getInstance() {
        return instance;
    }

    /**
     * Загружает конфигурацию из файла liquibase.properties.
     * Если в процессе возникает исключение IOException, оно отлавливается и сообщение об ошибке выводится в консоль.
     */
    private void loadConfig() {
        try (InputStream is = Config.class.getResourceAsStream("/db/changelog/liquibase.properties")) {
            configFile.load(is);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке конфигурации: " + e.getMessage());
        }
    }

    /**
     * Возвращает значения параметров конфигурации по ключу.
     *
     * @param key ключ, по которому требуется получить значение параметра конфигурации
     * @return значение параметра конфигурации или null, если такого ключа не найдено
     */
    public String getProperty(String key) {
        return configFile.getProperty(key);
    }
}
