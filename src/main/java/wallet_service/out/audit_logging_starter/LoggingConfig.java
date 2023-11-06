package wallet_service.out.audit_logging_starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class LoggingConfig {
    @Bean
    public LoggingConfig auditAndExecutionTimeAdvice() {
        return new LoggingConfig();
    }
}