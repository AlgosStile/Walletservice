package wallet_service.out.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {
    @Around("execution(* wallet_service.out.controller.*.*(..))")
    public Object auditAdvice(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("Аудит выполнен. Изменение в: " + jp.getSignature());
        return jp.proceed();
    }
}