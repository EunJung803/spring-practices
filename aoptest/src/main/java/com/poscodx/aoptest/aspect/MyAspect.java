package com.poscodx.aoptest.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// AOP 설정 빈
@Component
@Aspect
public class MyAspect {

	@Before("execution(public com.poscodx.aoptest.vo.ProductVo com.poscodx.aoptest.service.ProductService.find(String))")			// 이 메서드가 실행하기 전에 이 advice를 실행해라
	public void adviceBefore() {
		System.out.println("--- Before Advice ---");
	}

	@After("execution(com.poscodx.aoptest.vo.ProductVo com.poscodx.aoptest.service.ProductService.find(String))")		// public 생략 가능
	public void adviceAfter() {
		System.out.println("--- After Advice ---");
	}
	
	@AfterReturning("execution(* com.poscodx.aoptest.service.ProductService.find(..))")		// 모든 리턴값
	public void adviceAfterReturning() {
		System.out.println("--- AfterReturning Advice ---");
	}
	
	@AfterThrowing(value="execution(* *..*.*.*(..))", throwing="ex")		// *과 ..으로 패키지 이름 줄이기 가능 (현재 줄인거 내용 == 모든 패키지의 모든 클래스의 모든 메서드)
	public void adviceAfterThrowing(Throwable ex) {					// 에외를 받아야 함
		System.out.println("--- AfterThrowing Advice " + ex + "---");
	}
	
	@Around("execution(* *..*.ProductService.*(..))")
	public Object adviceAround(ProceedingJoinPoint pjp) throws Throwable {
		/* Before */
		System.out.println("--- Around (Before) ---");
		
		/* Point Cut Method 실행 */
		Object[] params = {"Camera"};
		Object result = pjp.proceed(params);		// 파마리터 바꿈
//		Object result = pjp.proceed();
		
		/* After */
		System.out.println("--- Around (Before) ---");
		
		return result;
	}
}
