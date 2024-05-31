package com.poscodx.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @RequestMapping 클래스 + 메소드 매핑
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping(value = "/joinform", method = RequestMethod.GET)
	public String joinform() {
		return "/WEB-INF/views/joinform.jsp";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVo vo) {
		System.out.println(vo);
		return "redirect:/";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public String update(@RequestParam("n") String name) {
		/**
		 * 만일 n이라는 이름의 파라미터가 없으면
		 * 400 Bad Request Error가 발생한다
		 */
		return "UserController.update(" + name + ")";
	}
	
	@ResponseBody
	@RequestMapping("/update2")
	public String update2(@RequestParam(value = "n", required = true, defaultValue = "") String name) {
		/**
		 * required가 있으면 발생 안함
		 * null 체크는 optional로 해도됨
		 * or default값으로 빈 String 받기
		 */
		return "UserController.update(" + name + ")";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public String list(@RequestParam(value = "p", required = true, defaultValue = "1") int pageNo) {
		// defaultValue는 int여도 "" 안에 있어야함
		return "UserController.update(" + pageNo + ")";
	}
	
	
}
