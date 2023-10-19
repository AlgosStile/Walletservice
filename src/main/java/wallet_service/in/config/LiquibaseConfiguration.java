package wallet_service.in.config;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import java.sql.Connection;
import java.sql.SQLException;

public class LiquibaseConfiguration {
    private static final String LIQUIBASE_CHANGELOG_LOCATION = "db/changelog/db.changelog-master.xml";


    public static void startLiquibase() throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            JdbcConnection liquibaseConn = new JdbcConnection(conn);

            Liquibase liquibase = new Liquibase(LIQUIBASE_CHANGELOG_LOCATION,
                    new ClassLoaderResourceAccessor(),
                    liquibaseConn);

            liquibase.update(new Contexts());
        } catch (LiquibaseException e) {
            System.out.println("Ошибка: " + e);
            throw new RuntimeException("Не смог начать Liquibase", e);
        } finally {


            // Здесь нужно, закрыть только в конце жизненного цикла приложения
            // Иначе Liquibase не сможет работать

        }
    }

}

