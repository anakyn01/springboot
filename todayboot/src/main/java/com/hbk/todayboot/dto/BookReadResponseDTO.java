package com.hbk.todayboot.dto;

import java.time.LocalDateTime;

import com.hbk.todayboot.entity.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor //매개변수가 없는 생성자를 자동으로 생성합니다
@Getter//이객체를 타임리프에서 사용할때 자바빈즈의 규칙에 따라 데이터를 가져오기 때문에 필요하다
public class BookReadResponseDTO {

	//필요한 필드지정
	private Integer bookId;
	private String title;
	private Integer price;
	private LocalDateTime insertDateTime;
	
	public BookReadResponseDTO fromBook(Book book) {
		//fromBook메소드는 Book엔티티를 매개변수로 받아서 내부의 값을 채우는 역활을 합니다
		this.bookId = book.getBookId();
		this.title = book.getTitle();
		this.price = book.getPrice();
		this.insertDateTime = book.getInsertDateTime();
		return this;
	}
	
	public static BookReadResponseDTO BookFactory(Book book) {
		BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
		bookReadResponseDTO.fromBook(book);
		return bookReadResponseDTO;
	}//사용할때마다 객체를 만들고 값을 설정하기 위해서 frombook메소드를 호출하는 일을 줄여줍니다
	
	
	
	
	
}
