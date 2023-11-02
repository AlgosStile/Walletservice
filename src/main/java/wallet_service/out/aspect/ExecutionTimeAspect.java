package wallet_service.out.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
/**
 * Класс ExecutionTimeAspect является аспектом, который предоставляет функциональность измерения времени выполнения методов контроллеров приложения Wallet Service.
 * Он используется для перехвата и логирования времени выполнения методов контроллеров.
 */
@Aspect
@Component
public class ExecutionTimeAspect {
    /**
     * Метод executionTimeAdvice является советом, который выполняет измерение времени выполнения методов контроллеров.
     *
     * @param jp объект ProceedingJoinPoint, представляющий точку сопряжения выполнения метода контроллера.
     * @return результат выполнения метода контроллера.
     * @throws Throwable если возникает исключение при выполнении метода контроллера.
     */
    @Around("execution(* wallet_service.out.controller.*.*(..))")
    public Object executionTimeAdvice(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = jp.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println(jp.getSignature() + " выполнен за " + executionTime + "мс");
        return proceed;
    }
}