package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Класс LoggingConfig представляет конфигурацию логирования и аудита.
 * Он используется для создания аспекта AuditAspect и включения автоматической проксировки аспектов.
 *
 * @author Олег Тодор
 */
@Configuration
@EnableAspectJAutoProxy
public class LoggingConfig {
    /**
     * Метод auditAndExecutionTimeAdvice создает экземпляр класса AuditAspect,
     * который будет использоваться для аудита и измерения времени выполнения методов.
     *
     * @return экземпляр класса AuditAspect для аудита и измерения времени выполнения
     */
    @Bean
    public LoggingConfig auditAndExecutionTimeAdvice() {
        return new LoggingConfig();
    }
}