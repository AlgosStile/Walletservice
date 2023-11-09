package org.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Класс AuditAspect является аспектом, реализующим возможность аудита и измерения времени выполнения
 * для методов контроллеров, расположенных в пакете wallet_service.out.controller.
 *
 * @author Олег Тодор.
 */
@Aspect
@Component
public class AuditAspect {

    @Around("execution(* org.wallet_service.out.controller.*.*(..))")
    public Object auditAndExecutionTimeAdvice(ProceedingJoinPoint jp) throws Throwable {

        System.out.println("Аудит выполнен. Изменение в: " + jp.getSignature());

        long start = System.currentTimeMillis();
        Object proceed = jp.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println(jp.getSignature() + " выполнен за " + executionTime + "мс");

        return proceed;
    }
}