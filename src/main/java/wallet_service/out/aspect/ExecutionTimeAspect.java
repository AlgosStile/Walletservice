package wallet_service.out.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("execution(* wallet_service.out.controller.*.*(..))")
    public Object executionTimeAdvice(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = jp.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println(jp.getSignature() + " выполнен за " + executionTime + "мс");
        return proceed;
    }
}