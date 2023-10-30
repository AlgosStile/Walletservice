package wallet_service.in.config;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final String configFile;

    public SwaggerConfig(@Value("${springfox.documentation.swagger-ui.config-file}") String configFile) {
        this.configFile = configFile;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis((Predicate<RequestHandler>) RequestHandlerSelectors.basePackage("wallet_service.out.controller")).paths((Predicate<String>) PathSelectors.any()).build().apiInfo(apiInfo()).enableUrlTemplating(false).enable(configFile != null && !configFile.isEmpty());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("WalletServiceApplication").description("WalletServiceApplication - Wallet Service").version("1.0.0").build();
    }

}
