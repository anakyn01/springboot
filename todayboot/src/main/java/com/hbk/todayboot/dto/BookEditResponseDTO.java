package com.hbk.todayboot.dto;

import java.time.LocalDateTime;

import com.hbk.todayboot.entity.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor //매개변수가 없는 생성자를 자동으로 생성합니다
@Getter//이객체를 타임리프에서 사용할때 자바빈즈의 규칙에 따라 데이터를 가져오기 때문에 필요하다
public class BookEditResponseDTO {

	private Integer bookId;
	private String title;
	private Integer price;
	private LocalDateTime inserDateTime;
	
	public BookEditResponseDTO fromBook(Book book) {//대입시킨걸
		this.bookId=book.getBookId();
		this.title = book.getTitle();
		this.price = book.getPrice();
		this.inserDateTime = book.getInsertDateTime();
		return this;
	}
	public static BookEditResponseDTO BookFactory(Book book) {//BookFactory 가 리턴
		BookEditResponseDTO bookEditResponseDTO = new BookEditResponseDTO();
		bookEditResponseDTO.fromBook(book);
		return bookEditResponseDTO;
		
	}
	
	
	
	
	
}
