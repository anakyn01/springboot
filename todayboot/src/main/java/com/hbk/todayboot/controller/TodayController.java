package com.hbk.todayboot.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hbk.todayboot.dto.BookCreateDTO;
import com.hbk.todayboot.dto.BookEditDTO;
import com.hbk.todayboot.dto.BookEditResponseDTO;
import com.hbk.todayboot.dto.BookListResponseDTO;
import com.hbk.todayboot.dto.BookReadResponseDTO;
import com.hbk.todayboot.service.BookService;

@Controller 
public class TodayController {//컨트롤러에서는 필드 의존성을 서비스에서는 생성자 의존성을 사용합니다
	//서비스 클래스는 다른곳에서 호출하기 위해 사용되는 클래스
	@Autowired//필드 의존성 주입 = private에 변수를 선언하고 위에 @Autowired 어노테이션을 붙여서 의존성 주입
	private BookService bookService;
	
	@GetMapping("/today/create")//get데이터를 가져올때 사용된는 메소드
	public String create() {//http에서 GET,POST,PATCH,PUT,DELETE
		return "today/create";
	}
	@PostMapping("/today/create")
	public String insert(BookCreateDTO bookCreateDTO) {
		Integer bookId = this.bookService.insert(bookCreateDTO);
		return String.format("redirect:/today/read/%s", bookId);
	}
	
	@GetMapping("/today/read/{bookId}")//상세 화면의 경로 지정
	public ModelAndView read(@PathVariable Integer bookId) {
		//@GetMapping에 정의된 경로 매개변수는 컨트롤러 메소드의 파라미터로 정의합니다
		//ModelAndView 데이터와 화면을 함께 담을수 있는 객체
		ModelAndView mav = new ModelAndView();
		try {
			BookReadResponseDTO bookReadResponseDTO = this.bookService.read(bookId);
			mav.addObject("bookReadResponseDTO", bookReadResponseDTO);//뷰에 전달할 데이터 설정
			mav.setViewName("today/read");
			
		}catch(NoSuchElementException ex) {
			mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
			mav.addObject("message","정보가 없으 므니다");
			mav.addObject("location","/today");
			mav.setViewName("common/error/422");//오류 200(ok) 400(bad request), 
		}
		return mav;
	}
	//예외가 발생하면 실행되는 메소드
	@ExceptionHandler(NoSuchElementException.class)
	public ModelAndView noSuchElementExceptionHandler(NoSuchElementException ex) {
		ModelAndView mav = new ModelAndView();//객체 생성
		mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		mav.addObject("message","정보가 없으 므니다");
		mav.addObject("location","/today/list");
		mav.setViewName("common/error/422");//오류 200(ok) 400(bad request), 
		return mav;
	}
	private ModelAndView error422(String message, String location) {//메세지와 이동할 페이지를 매개변수로 받는다
		ModelAndView mav = new ModelAndView();
		mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		mav.addObject("message",message);
		mav.addObject("location",location);
		mav.setViewName("common/error/422");//오류 200(ok) 400(bad request), 
		return mav;
	}
	
	
	//수정
	@GetMapping("/today/edit/{bookId}")
	public ModelAndView edit(@PathVariable Integer bookId) throws NoSuchElementException{
		ModelAndView mav = new ModelAndView();
		BookEditResponseDTO bookEditResponseDTO = this.bookService.edit(bookId);
		mav.addObject("bookEditResponseDTO",bookEditResponseDTO);
		mav.setViewName("today/edit");
		return mav;
	}
	@PostMapping("/today/edit/{bookId}")
	public ModelAndView update(@Validated BookEditDTO bookEditDTO, Errors errors) {
		if(errors.hasErrors()) {
			String errorMessage = 
errors.getFieldErrors().stream().map(x -> x.getField() + " : " +x.getDefaultMessage()).collect(Collectors.joining("\n"));
return this.error422(errorMessage, String.format("/today/edit/%s", bookEditDTO.getBookId()));
		}
	this.bookService.update(bookEditDTO);
	ModelAndView mav = new ModelAndView();
	mav.setViewName(String.format("redirect:/today/read/%s", bookEditDTO.getBookId()));
		
		return mav;
	}
	
	
	//삭제 매개변수 한개밖에 없을때에는 DTO만들지 않고 곧바로 파라미터로 입력을 받습니다
	@PostMapping("/today/delete")
	public String delete(Integer bookId) throws NoSuchElementException{
		this.bookService.delete(bookId);
		return "redirect:/today/list";
	}
	
	@GetMapping(value= {"/today/list", "/today"})
	public ModelAndView bookList(String title, Integer page, ModelAndView mav) {
		mav.setViewName("/today/list");
		List<BookListResponseDTO> books = this.bookService.bookList(title, page);
		mav.addObject("books", books);
		return mav;
	}
	
	
	
	
	
	
	
	
	

}
/*
@Controller 어노테이션이 있는 클래스는 스프링부트가 브라우저의 요청(request)를 받아들이는 
컨트롤러라고 인지 해서 자바빈(java bean)으로 등록해서 관리하게 됩니다
프레임워크에서 관리하는 클래스가 됩니다
*/
