package wallet_service;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import wallet_service.in.config.Config;
import wallet_service.in.config.SwaggerConfig;

public class WalletServiceApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("wallet_service.in.config", "wallet_service.out.service", "wallet_service.out.repository", "wallet_service.out.aspect", "wallet_service.out.controller", "wallet_service.out.model");
        context.register(Config.class, SwaggerConfig.class);
        context.refresh();


        context.close();
    }

}