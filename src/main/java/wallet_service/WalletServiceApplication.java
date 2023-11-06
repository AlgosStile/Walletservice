package wallet_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@EnableAspectJAutoProxy
public class WalletServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WalletServiceApplication.class, args);
    }
}

