package wallet_service.in.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Config instance = new Config();
    private final Properties configFile;

    private Config() {
        configFile = new Properties();
        loadConfig();
    }

    public static Config getInstance() {
        return instance;
    }

    private void loadConfig() {
        try (InputStream is = Config.class.getResourceAsStream("/db/changelog/liquibase.properties")) {
            configFile.load(is);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке конфигурации: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return configFile.getProperty(key);
    }
}