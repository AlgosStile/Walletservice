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
        try (InputStream is = Config.class.getResourceAsStream("/db.properties")) {
            configFile.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return configFile.getProperty(key);
    }
}