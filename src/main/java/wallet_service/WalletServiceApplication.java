package wallet_service;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import wallet_service.in.config.Config;
import wallet_service.in.config.SwaggerConfig;

public class WalletServiceApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("wallet_service");
        context.register(Config.class, SwaggerConfig.class);
        context.refresh();


        context.close();
    }

}