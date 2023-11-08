package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс LoggingConfig представляет конфигурацию логирования и аудита.
 * Он используется для создания аспекта AuditAspect и включения автоматической проксировки аспектов.
 *
 * @author Олег Тодор
 */
@Configuration
public class LoggingConfig {

    @Bean
    public LoggingConfig auditAndExecutionTimeAdvice() {
        return new LoggingConfig();
    }
}