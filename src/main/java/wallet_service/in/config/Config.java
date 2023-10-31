package wallet_service.in.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
/**
 * Класс Config является конфигурационным классом для приложения Wallet Service.
 * Он используется для создания и настройки необходимых компонентов и бинов, а также для указания различных аннотаций и настроек для работы приложения.
 */
@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "wallet_service.out.repository")
@ComponentScan({"wallet_service.in.*", "wallet_service.out.*"})
public class Config {
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public Config(@Value("${db.url}") String dbUrl,
                  @Value("${db.username}") String dbUsername,
                  @Value("${db.password}") String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    /**
     * Метод для создания и настройки DataSource для подключения к базе данных PostgreSQL.
     *
     * @return DataSource объект, представляющий подключение к базе данных.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }


}




