package main.java.wallet_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Класс WalletServiceApplication представляет главный класс приложения кошелька,
 * который использует фреймворк Spring Boot.
 *
 * @Author Олег Тодор
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class WalletServiceApplication {
    /**
     * Главный метод, запускающий приложение кошелька.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(WalletServiceApplication.class, args);
    }
}


