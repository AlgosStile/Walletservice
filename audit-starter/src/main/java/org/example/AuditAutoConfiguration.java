package org.example;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(AuditAspect.class)
public class AuditAutoConfiguration {

    @Bean
    public AuditAspect auditAspect() {
        return new AuditAspect();
    }

}

