package com.chaboo.gobill.aop;

import com.chaboo.gobill.aop.annotation.LogTrace;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class LogAspect {

  // 특정 어노테이션이 명시된 모든 메써드의 실행 전후로 끼어들 수 있다.
  @Around("@annotation(logTrace)")
  public Object doSomethingAround(final ProceedingJoinPoint joinPoint, final LogTrace logTrace)
      throws Throwable {
    // 대상 메서드 실행 전 끼어들어 실행할 로직을 작성
    long start = System.currentTimeMillis();
    System.out.println(">>>>>>>>>>>>>>>>>>>> start: "+ start);
    Object result = joinPoint.proceed();
    // 대상 메서드 실행 후 끼어들어 실행할 로직을 작성, 리턴 값을 가공할 수 있다.

    long executionTime = System.currentTimeMillis() - start;
    System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
    return result;
  }

//  // 메서드 레벨에 특정 어노테이션이 명시된 모든 메서드의 실행 전후로 끼어들 수 있다.
//  @Around("@annotation(scheduled)")
//  public Object process(final ProceedingJoinPoint joinPoint, final Scheduled scheduled)
//      throws Throwable {
//
//  }
//
//  // 클래스 레벨에 특정 어노테이션이 명시된 모든 메드의 실행 전후로 끼어들 수 있다.
//  @Around("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Service) || @within(org.springframework.stereotype.Repository)")
//  public Object process(final ProceedingJoinPoint joinPoint) throws Throwable {
//
//  }


}
