package wallet_service.in.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;
    private String url;
    private String username;
    private String password;

    private DBConnection(){
        try {
            Config myConfig = new Config("db.properties");
            this.url = myConfig.getProperty("db.url");
            this.username = myConfig.getProperty("db.user");
            this.password = myConfig.getProperty("db.password");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public synchronized static DBConnection getInstance(){
        if(instance == null){
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }
}