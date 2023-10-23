package wallet_service.out.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AuditAspect  {

    @Pointcut("execution(* wallet_service.out.controller.*.*(..))")
    private void selectAll(){

    }

    @After("selectAll()")
    public void afterAdvice(JoinPoint jp){
        System.out.println("Аудит выполнен. Изменение в: "+ jp.getSignature());
    }

}