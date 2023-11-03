package wallet_service.in.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import wallet_service.out.aspect.AuditAspect;
import wallet_service.out.aspect.ExecutionTimeAspect;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {
    @Bean
    public AuditAspect auditAspect() {
        return new AuditAspect();
    }

    @Bean
    public ExecutionTimeAspect executionTimeAspect() {
        return new ExecutionTimeAspect();
    }
}