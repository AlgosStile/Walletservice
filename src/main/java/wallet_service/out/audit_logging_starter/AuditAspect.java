package wallet_service.out.audit_logging_starter;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {
    @Around("execution(* wallet_service.out.controller.*.*(..))")
    public Object auditAndExecutionTimeAdvice(ProceedingJoinPoint jp) throws Throwable {

        System.out.println("Аудит выполнен. Изменение в: " + jp.getSignature());

        long start = System.currentTimeMillis();
        Object proceed = jp.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println(jp.getSignature() + " выполнен за " + executionTime + "мс");

        return proceed;
    }
}