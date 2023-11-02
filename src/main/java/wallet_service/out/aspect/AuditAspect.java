package wallet_service.out.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Класс AuditAspect является аспектом, который предоставляет функциональность аудита для контроллеров приложения Wallet Service.
 * Он используется для перехвата и логирования выполнения методов контроллеров.
 */
@Aspect
@Component
public class AuditAspect {
    /**
     * Метод auditAdvice является советом, который выполняет аудит изменений в контроллерах.
     *
     * @param jp объект ProceedingJoinPoint, представляющий точку сопряжения выполнения метода контроллера.
     * @return результат выполнения метода контроллера.
     * @throws Throwable если возникает исключение при выполнении метода контроллера.
     */
    @Around("execution(* wallet_service.out.controller.*.*(..))")
    public Object auditAdvice(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("Аудит выполнен. Изменение в: " + jp.getSignature());
        return jp.proceed();
    }
}