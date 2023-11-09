package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс LoggingAspect представляет конфигурацию логирования и аудита.
 * Он используется для создания аспекта AuditAspect и включения автоматической проксировки аспектов.
 *
 * @author Олег Тодор
 */
@Configuration
public class LoggingAspect {

    @Bean
    public LoggingAspect auditAndExecutionTimeAdvice() {
        return new LoggingAspect();
    }
}