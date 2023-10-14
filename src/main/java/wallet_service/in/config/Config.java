package wallet_service.in.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final Properties configFile;

    public Config(String configFilename) {
        configFile = new java.util.Properties();
        try {
            InputStream is = Config.class.getResourceAsStream("/" + configFilename);
            configFile.load(is);
        } catch (IOException eta) {
            eta.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return this.configFile.getProperty(key);
    }
}