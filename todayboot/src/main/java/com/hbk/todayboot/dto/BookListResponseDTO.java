package com.hbk.todayboot.dto;

import lombok.Getter;

@Getter
public class BookListResponseDTO {
	//제목과 번호를 가져오는 클래스를 만듬 다른 responsedto와 다르게 fromBook, BookFactory메소드가 없습니다
	private Integer bookId;
	private String title;
	
	//생성자를 통해 객체를 생성하는 방법은 변하지 않는 (immutable)객체를 생성할때 자주 사용하는 패턴입니다
	//맴버변수 private 로 선언되어 있고 setter도 없기 때문에 객체를 생성할때 외에는 값을 바꿀수 없습니다
	public BookListResponseDTO(Integer bookId, String title) {
		this.bookId = bookId;
		this.title = title;
	}

}
