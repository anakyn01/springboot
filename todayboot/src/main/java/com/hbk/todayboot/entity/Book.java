package com.hbk.todayboot.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //이클래스가 엔티티 클래스야라고 알려줍니다
@Data//자동으로 자바빈즈의 getter,setter,toString,equals.hashCode를 만듭니다
@Builder//객체를 생성할때 빌더패턴으로 생성할수 있게 합니다
@NoArgsConstructor//엔티티에 값을 채울때 필요합니다
//jpa -> 엔티티의 인스턴스를 생성하고 데이터 베이스에 있는 값을 일겅서 setter 를 통해서 인스턴스의 맴버 변수값을 채워 갑니다
//그래서 jpa가 엔티티의 인스턴스를 생성할때는 매개변수 없는 생성자가 있어야 합니다
@AllArgsConstructor//모든 맴버변수를 매개변수로 설정할수 있는 생성자를 자동으로 만들어 주는 
public class Book {
	
@Id//데이터 베이스 행에 유일 식별자 데이터베이스 관점에서 보자면 PK입니다
@GeneratedValue(strategy = GenerationType.IDENTITY)
//GeneratedValue 자동 생성되는 값이라는 것을 나타내기 위해 사용합니다
//GenerationType.IDENTITY는 데이터베이스에 키 생성을 위임한다는 의미 입니다
//mYSQL이나 MS-SQL은 자동PK 증가 기능이 있는데 이기능을 활성화 합니다
private Integer bookId;//게시글에 순번

@Column(length = 200)//데이터 베이스 열에 특성을 나타냅니다 length속성으로 문자열에 길이를 제한할수 있습니다
//name으로 컬럼이름을 나타내거나 nullable로 값이 비어있는지 여부도 설정 할수 있습니다
private String title;

@Column(length = 200)
private Integer price;

@CreationTimestamp //자동으로 시간을 설정하는 어노테이션 자바8에서 도입
private LocalDateTime insertDateTime;	

}
/*
Java Persistence API : 자바에서 객체에 데이터베이스에 저장하고 관리하기위한 인터페이스 기능을 제공하는 API
Object Relation Mapping

jpa는 데이터베이스 테이블을 언어의 객체처럼 다루는 ORM기술중에 하나입니다
*/
