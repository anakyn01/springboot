package com.hbk.todayboot.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.hbk.todayboot.entity.Book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookEditDTO {
	
@Positive //값을 양수로 제한
private Integer bookId;

@NotBlank//null이거나 문자열이 비어있는면 No
private String title;

@Min(1000)//1000원 이하이면 안됨
private Integer price;

public Book fill(Book book) {
	//fill과 같은 메소드를 사용하면 레이어에서 값을 채우는 논리가 커멘드 객체로 이동되므로 코드를 좀더 분산시킬수 있다
	book.setTitle(this.title);
	book.setPrice(this.price);
	return book;
}





}
