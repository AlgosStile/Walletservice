package org.example;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(LoggingConfig.class)
public class LoggingAutoConfiguration {

    @Bean
    public LoggingConfig loggingAspect() {
        return new LoggingConfig();
    }

}

