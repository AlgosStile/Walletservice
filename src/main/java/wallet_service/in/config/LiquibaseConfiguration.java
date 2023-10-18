package wallet_service.in.config;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import wallet_service.in.config.DBConnection;

import java.util.Properties;

public class LiquibaseConfiguration {

    private static final String LIQUIBASE_CHANGELOG_LOCATION = "db/changelog/db.changelog-master.xml";
    private static final String LIQUIBASE_PROPERTY_FILE = "liquibase.properties";

    private final DBConnection dbConnection;

    public LiquibaseConfiguration(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void updateDatabase() {
        try {
            DatabaseConnection databaseConnection = new JdbcConnection(this.dbConnection.getConnection());
            Liquibase liquibase = new Liquibase(LIQUIBASE_CHANGELOG_LOCATION, new ClassLoaderResourceAccessor(), databaseConnection);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException e) {
            System.err.println("Ошибка при выполнении Liquibase: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Properties getLiquibaseProperties() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(LIQUIBASE_PROPERTY_FILE));
        } catch (Exception e) {
            System.err.println("Не удалось загрузить свойства Liquibase: " + e.getMessage());
            e.printStackTrace();
        }
        return properties;
    }
}
