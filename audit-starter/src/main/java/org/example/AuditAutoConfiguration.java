package org.example;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ConditionalOnClass(AuditAspect.class)
@EnableAspectJAutoProxy
public class AuditAutoConfiguration {

    @Bean
    public AuditAspect auditAspect() {
        return new AuditAspect();
    }

}

