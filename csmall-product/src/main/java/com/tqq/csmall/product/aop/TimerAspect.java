package com.tqq.csmall.product.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class TimerAspect {

    @Around("execution(* com.tqq.csmall.product.services.*.*(..))")
    public Object timer(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        /*获取匹配的方法的相关信息*/
        String targetClassName = proceedingJoinPoint.getClass().getName();
        String signatureName = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        System.out.println("方法类型："+targetClassName);
        System.out.println("方法名："+signatureName);
        System.out.println("参数列表："+ Arrays.toString(args));
        /*执行以上表达式匹配的方法*/
        Object result = proceedingJoinPoint.proceed();

        long end = System.currentTimeMillis();
        System.out.println("执行耗时："+ (end-start)+"ms");
        return result;
    }
}
