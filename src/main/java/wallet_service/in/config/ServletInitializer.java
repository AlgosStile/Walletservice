package main.java.wallet_service.in.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import main.java.wallet_service.WalletServiceApplication;

/**
 * Класс ServletInitializer является наследником класса SpringBootServletInitializer
 * и предназначен для инициализации приложения на основе класса WalletServiceApplication
 * при использовании контейнера сервлетов.
 *
 * @author Олег Тодор.
 */
public class ServletInitializer extends SpringBootServletInitializer {
    /**
     * @param application объект SpringApplicationBuilder, представляющий наше приложение
     * @return возвращает настроенный объект SpringApplicationBuilder с указанием
     * класса WalletServiceApplication в качестве источника исходного кода.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WalletServiceApplication.class);
    }
}
