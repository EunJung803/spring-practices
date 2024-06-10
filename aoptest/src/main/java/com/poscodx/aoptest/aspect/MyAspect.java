package com.poscodx.aoptest.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
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
	
	@AfterReturning("execution(* com.poscodx.aoptest.service.ProductService.find(..))")		// 모든 리턴값, *과 ..으로 패키지 이름 줄이기 가능
	public void adviceAfterReturning() {
		System.out.println("--- AfterReturning Advice ---");
	}
}
