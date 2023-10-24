package wallet_service.out.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Аспект аудита.
 * Этот класс представляет аспект аудита, который отслеживает выполнение методов в определенном пакете контроллеров.
 * После выполнения каждого метода выводит информацию о выполненном изменении.
 *
 * @author Олег Тодор
 */
@Aspect
public class AuditAspect {

    /**
     * Точка среза для выбора всех методов в пакете контроллеров.
     */
    @Pointcut("execution(* wallet_service.out.controller.*.*(..))")
    private void selectAll() {

    }

    /**
     * После-совет выполняется после выполнения всех методов, выбранных точкой среза selectAll().
     *
     * @param jp Объект JoinPoint, представляющий выполненный метод.
     */
    @After("selectAll()")
    public void afterAdvice(JoinPoint jp) {
        System.out.println("Аудит выполнен. Изменение в: " + jp.getSignature());
    }
}