package wallet_service.in.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import wallet_service.out.aspect.AuditAspect;
import wallet_service.out.aspect.ExecutionTimeAspect;


@Configuration
@Aspect
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
    @Around("execution(* wallet_service.out.controller.*.*(..))")
    public Object auditAdvice(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("Аудит выполнен. Изменение в: " + jp.getSignature());
        return jp.proceed();
    }
    @Around("execution(* wallet_service.out.controller.*.*(..))")
    public Object executionTimeAdvice(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = jp.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println(jp.getSignature() + " выполнен за " + executionTime + "мс");
        return proceed;
    }

}