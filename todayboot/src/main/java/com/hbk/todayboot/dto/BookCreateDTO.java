package com.hbk.todayboot.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/*
Data Transfer Object => 값을 담는 컨테이너 객체
*/
@Getter
@Setter //자바빈즈의 getter와 setter를 만들어 줍니다
public class BookCreateDTO {

	@NonNull//값이 null일 경우 NullpointerException을 발생 반드시 값이 있다는걸 보장하기 위해 필수인 파라미터에 붙여줍니다
	private String title;
	
	@NonNull
	private Integer price;
}
