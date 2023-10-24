package wallet_service.out.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Аспект, отвечающий за измерение времени выполнения методов контроллеров в пакете `wallet_service.out.controller`.
 *
 * @author Олег Тодор
 */
@Aspect
public class ExecutionTimeAspect {

    /**
     * Аннотация `@Around` указывает, что данный метод будет выполняться вокруг вызовов методов, указанных в выражении pointcut.
     * В данном случае, метод `executionTimeAdvice` будет применяться к методам в пакете `wallet_service.out.controller`.
     *
     * @param jp Объект `ProceedingJoinPoint`, представляющий собой точку соединения (join point) во время выполнения программы.
     * @return Объект, возвращаемый методом, к которому применяется аспект.
     * @throws Throwable Если при выполнении метода возникает исключение, оно будет проброшено.
     */
    @Around("execution(* wallet_service.out.controller.*.*(..))")
    public Object executionTimeAdvice(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis(); // Запоминаем текущее время до выполнения метода
        Object proceed = jp.proceed(); // Выполняем целевой метод
        long executionTime = System.currentTimeMillis() - start; // Вычисляем время выполнения метода
        System.out.println(jp.getSignature() + " выполнен за " + executionTime + "мс"); // Выводим информацию о времени выполнения
        return proceed; // Возвращаем результат выполнения метода
    }
}