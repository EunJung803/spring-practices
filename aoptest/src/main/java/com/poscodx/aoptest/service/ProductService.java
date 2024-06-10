package com.poscodx.aoptest.service;

import org.springframework.stereotype.Service;

import com.poscodx.aoptest.vo.ProductVo;

@Service
public class ProductService {
	
	public ProductVo find(String name) {
		System.out.println("[ProductService] finding...");
		
		// AfterThrowing을 실험하기 위한 예외 던지기
//		if(1-1 == 0) {
//			throw new RuntimeException("ProductService.find() Exception");
//		}
		
		return new ProductVo(name);
	}
	
}
