package wallet_service.in.config;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Класс LiquibaseConfiguration предназначен для настройки и инициализации Liquibase,
 * инструмент миграции базы данных.
 * Liquibase использует XML файл конфигурации скриптов миграции,
 * находящийся по пути "db/changelog/db.changelog-master.xml".
 *
 * @author Олег Тодор
 * @since 2.0.0
 */
public class LiquibaseConfiguration {
    //местоположение файла изменений Liquibase
    private static final String LIQUIBASE_CHANGELOG_LOCATION = "db/changelog/db.changelog-master.xml";

    /**
     * Метод StartLiquibase инициализирует и начинает выполнение Liquibase, используя предоставленный
     * файл скриптов миграции. Метод использует подключение к базе данных, предоставляемое DBConnection.
     * В случае ошибок при инициализации или выполнении Liquibase, метод генерирует исключение.
     *
     * @throws SQLException в случае, если подключение к базе данных не было установлено.
     */
    public static void startLiquibase() throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getInstance().getConnection();
            if (conn == null) {
                throw new SQLException("Database connection could not be established");
            }
            JdbcConnection liquibaseConn = new JdbcConnection(conn);

            // Создание объекта Liquibase и начало обновления
            Liquibase liquibase = new Liquibase(LIQUIBASE_CHANGELOG_LOCATION,
                    new ClassLoaderResourceAccessor(),
                    liquibaseConn);
            liquibase.update(new Contexts());
        } catch (LiquibaseException e) {
            System.out.println("Ошибка: " + e);
            throw new RuntimeException("Не смог начать Liquibase", e);
        } finally {
            // Здесь закрываем подключение в конце жизненного цикла приложения
            // Иначе Liquibase error
            // Обеспечение закрытия подключения важно для устранения утечек ресурсов
        }
    }
}
