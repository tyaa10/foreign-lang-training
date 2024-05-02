package org.tyaa.training.server.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Configuration;
import org.tyaa.training.server.models.ResponseModel;
import org.tyaa.training.server.utils.ErrorsGetter;

/**
 * Набор служебных методов, внутри которых будут вызываться основные методы приложения
 * для перехвата и обработки исключительных ситуаций
 * */
@Aspect
@Configuration
public class ExceptionHandlerAspect {
    /**
     * Обработка исключений в репозиториях
     * */
    @Around("execution(* org.tyaa.training.server.repositories.*.*(..))")
    public Object onRepositoryMethodExecution(ProceedingJoinPoint pjp) throws Exception {
        System.out.println("onRepositoryMethodExecution");
        Object output = null;
        try {
            output = pjp.proceed();
        } catch (Exception ex) {
            System.out.println("onRepositoryMethodException: " + ex.getMessage());
            if (ex.getMessage() != null && ex.getMessage().contains("users_name_key")) {
                System.out.println("onRepositoryMethodException handled");
                throw new ConstraintViolationException("", null, "");
            }
            throw ex;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return output;
    }

    /**
     * Обработка исключений в службах
     * */
    @Around("execution(* org.tyaa.training.server.services.*.*(..))")
    public Object onServiceMethodExecution(ProceedingJoinPoint pjp) {
        System.out.println("onServiceMethodExecution");
        Object output = null;
        try {
            output = pjp.proceed();
        } catch (ConstraintViolationException ex) {
            System.out.println("onServiceMethodException handled");
            output =
                    ResponseModel.builder()
                            .status(ResponseModel.FAIL_STATUS)
                            .message("This name is already taken")
                            .build();
        } catch (Exception ex) {
            System.err.println("Unknown Error");
            ex.printStackTrace();
            output =
                    ResponseModel.builder()
                            .status(ResponseModel.FAIL_STATUS)
                            .message("Unknown Error")
                            .build();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return output;
    }
}
