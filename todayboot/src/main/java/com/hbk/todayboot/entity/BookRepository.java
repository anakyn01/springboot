package com.hbk.todayboot.entity;

import java.util.List;

import org.springframework.data.domain.Pageable;//페이징과 정렬 정보를 담고 있는 인터페이스
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
	
	public List<Book> findByTitleContains(String title, Pageable pageable);

}
//제너릭 타입 결과값을 지정된 형식으로 리턴 T => 엔티티 타입 bOOK , 두번째 인수 ID는 엔티티의 pk타입 입니다