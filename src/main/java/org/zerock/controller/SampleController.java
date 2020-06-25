package org.zerock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
	
	/*
	 * @InitBinder public void initBinder(WebDataBinder binder) { SimpleDateFormat
	 * dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 * binder.registerCustomEditor(java.util.Date.class, new
	 * CustomDateEditor(dateFormat, false)); }
	 */
	
	@RequestMapping("")
	public void basic() {
		log.info("basic.................");
	}

	@RequestMapping(value = "/basic", method = { RequestMethod.GET, RequestMethod.POST })
	public void basicGet() {
		log.info("basic get..................");
	}

	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		log.info("basic get only get..................");
	}
	
	
	/* 컨트롤러의 파라미터 수집 */
	@GetMapping("/ex01")	// localhost:8080/sample/ex01?name=AAA&age=10
	public String ex01(SampleDTO dto) {
		log.info("" + dto);

		return "ex01";
	}
	
	/* @RequestParam 변수와 파라미터의 이름이 달라도 됨 */
	@GetMapping("/ex02")	// localhost:8080/sample/ex02?name=AAA&age=10
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		log.info("name: " + name);
		log.info("age: " + age);

		return "ex02";
	}
	
	/* 리스트, 배열 처리 */
	@GetMapping("/ex02List")	// localhost:8080/sample/ex02List?ids=111&ids=222&ids=333
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("ids: " + ids);

		return "ex02List";
	}

	@GetMapping("/ex02Array")	// localhost:8080/sample/ex02Array?ids=111&ids=222&ids=333
	public String ex02Array(@RequestParam("ids") String[] ids) {
		log.info("ids: " + Arrays.toString(ids));

		return "ex02Array";
	}

	@GetMapping("/ex02Bean")	// '%5B'='[', '%5D'=']', localhost:8080/sample/ex02Bean?list%5B0%5D.name=aaa&list%5B2%5D.name=bbb	
	public String ex02Bean(SampleDTOList list) {
		log.info("list dtos: " + list);

		return "ex02Bean";
	}

	/* @InitBinder 바인딩, 파라미터 변환해서 처리 1. @InitBinder Method 2. @DateTimeFormat(pattern = "yyyy-MM-dd") */
	@GetMapping("/ex03")	// localhost:8080/sample/ex03?title=test&dueDate=2018-01-01
	public String ex03(TodoDTO todo) {
		log.info("todo: " + todo);
		return "ex03";
	}
	
	/* @ModelAttribute 기본자료형의 데이터를 화면으로 전달 */
	@GetMapping("/ex04")	// localhost:8080/sample/ex04?name=aaa&age=11&page=9
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
		log.info("dto: " + dto);
		log.info("page: " + page);
		
		return "/sample/ex04";	// 파일 위치
	}


	/* Controller의 리턴 타입 */
	/* void */
	@GetMapping("/ex05")    // // localhost:8080/sample/ex05
	public void ex05() {
		log.info("/ex05.........");
	}
    
	/* 객체 타입, @ResponseBody json 데이터 */
	@ResponseBody
	@GetMapping("/ex06")    // localhost:8080/sample/ex06
	public SampleDTO ex06() {    
		log.info("/ex06.........");
        
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
        
		return dto;
	}
    
	/* ResponseEntity 타입, 헤더 메시지와 상태 코드 변환,전달 */
	@GetMapping("/ex07")    // // localhost:8080/sample/ex07
	public ResponseEntity<String> ex07() {
		log.info("/ex07..........");
        
		// {"name": "홍길동"}
		String msg = "{\"name\": \"홍길동\"}";
        
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
        
		return new ResponseEntity<String>(msg, header, HttpStatus.OK);
	}
}
