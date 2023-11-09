package main.java.org.wallet_service;

import org.example.EnableLogging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * Входная точка для приложения основанного на Spring Boot, называемого "WalletService".
 * Этот сервис является частью пакета main.java.org.wallet_service.
 *
 * @author Олег Тодор
 * @version 5.0
 */
@SpringBootApplication
@EnableLogging
@Configuration
public class WalletServiceApplication {
    /**
     * Основной метод для запуска приложения.
     *
     * @param args аргументы командной строки, которые можно передать при запуске
     *             приложения.
     */
    public static void main(String[] args) {
        SpringApplication.run(WalletServiceApplication.class, args);
    }
}


