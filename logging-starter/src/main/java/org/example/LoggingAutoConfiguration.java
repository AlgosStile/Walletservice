package org.example;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ConditionalOnClass(LoggingConfig.class)
@EnableAspectJAutoProxy
public class LoggingAutoConfiguration {

    @Bean
    public LoggingConfig loggingAspect() {
        return new LoggingConfig();
    }

}

