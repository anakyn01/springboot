package com.hbk.todayboot.service;
//서비스 클래스 실제 비즈니스로직이 실행되는 곳

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.hbk.todayboot.dto.BookCreateDTO;
import com.hbk.todayboot.dto.BookEditDTO;
import com.hbk.todayboot.dto.BookEditResponseDTO;
import com.hbk.todayboot.dto.BookListResponseDTO;
import com.hbk.todayboot.dto.BookReadResponseDTO;
import com.hbk.todayboot.entity.Book;
import com.hbk.todayboot.entity.BookRepository;



@Service
public class BookService {

	private BookRepository bookRepository;//데이터 베이스와 통신하기 위한 객체를 선언
	//이렇게 클래스안에 다른 클래스의 인스턴스를 가지고 있는 것을 합성(composition)이라 부릅니다
	
	public BookService(BookRepository bookRepository) {//생성자를 통해 외부 자원을 주입받는
		//방식을 생성자 의존성 주입이라고 한다
		this.bookRepository = bookRepository;
	}
	//기존 개발의 패러다임은 클래스가 내부에서 사용할 객체를 직접만듬 스프링은 이개념을 뒤집어서 
	//완성된 객체를 주입받는 방식을 사용합니다 이런 방식을 의존성 주입(Dependency Injection)
	//이라고 부릅니다
	
	public Integer insert(BookCreateDTO bookCreateDTO) {//엔티티 객체
		Book book = Book.builder()//객체 빌더생성
		.title(bookCreateDTO.getTitle())//타이틀 데이터 세팅
		.price(bookCreateDTO.getPrice())//프라이스 데이터 세팅
		.build(); //book객체 생성
		this.bookRepository.save(book);//데이터베이스에 저장합니다
		return book.getBookId();
		//생성이 성공하면 보기 화면으로 전환하는데 pk를 반환해서 보기화면으로 이동합니다
				
	}
	
	//읽기 메소드
	public BookReadResponseDTO read(Integer bookId) throws NoSuchElementException{
		Book book = this.bookRepository.findById(bookId).orElseThrow(null);
		BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
		bookReadResponseDTO.fromBook(book);
		return bookReadResponseDTO;
	}
	/*
	read(Integer bookId) 고유번호(pk)로 정보를 불러와야 하므로 매개변수로 bookId를 사용합니다
	bookRepository는 입력기능을 만들때 의존성을 주입받은 JpaRepository인터페이스 입니다
	대부분에 웹 프로그래밍은 객체를 조회할때 조건이 대부분 id(PK)
	RDBMS의 행을 고유하게 구분할수 있는 값이 PK입니다
	findById 메소드가 파라미터로 받아들이는 타입은 JPA리포지토리에 의해서 결정됩니다
	findById는 Optional<Book>객체를 리턴합니다
	Optional 객체는 값이 null일수도 있는 객체로 실제로 값이 없더라도 null을 반환하는게 아니라
	Optional변수.isPresent() == false 인 Optional객체를 리턴합니다
	Optional을 사용하면 꼭 필요할때만 null체크하는 장점이 있습니다
	orElseThrow라는 메소드가 있는데 만약 내부 값이  null이라면 예외를 던지는 기능을 합니다
	orElseThrow 던지는 예외는 NoSuchElementException입니다
	*/
	
	//수정
	public BookEditResponseDTO edit(Integer bookId) throws NoSuchElementException{
		Book book = this.bookRepository.findById(bookId).orElseThrow(null);
		return BookEditResponseDTO.BookFactory(book);
	}
	public void update(BookEditDTO bookEditDTO) throws NoSuchElementException{
		Book book = this.bookRepository.findById(bookEditDTO.getBookId()).orElseThrow(null);
		book = bookEditDTO.fill(book);
		this.bookRepository.save(book);
	}
	
	//삭제 jpa에서는 생성을 제외하고 읽기, 수정, 삭제는 항상 조회 -> 뭔가 처리
	public void delete(Integer bookId)throws NoSuchElementException{
		Book book = this.bookRepository.findById(bookId).orElseThrow(null);
		this.bookRepository.delete(book);
	}
	
	public List<BookListResponseDTO> bookList(String title, Integer page){
		//매개변수 title은 제목 검색을 위해서 page는 현재 페이지를 나타내기 위해서 선언되었음
		final int pageSize = 10;//페이징에서 한페이지에 보여지는 페이지수 1페이지에 글이 10개 나옵니다
		//변경되지 않는 값이라는 걸 명시합니다
		List<Book> books;
		
		if(page == null) {//자바는 메소드 오버로딩을 지원하는 대신 매개변수 기본값 기능이 없습니다 
//그래서  page객체는 null을 허용하는 Integer타입으로 선언후에 만약 페이지 변수가 null이라면 기본값을 0으로 설정합니다
			page = 0;//jpa에서 페이지에 시작입니다
		}else {
			page -= 1;//다만 일반적으로 사용자들은 1페이지부터 시작한다고 가정하기 때문에 그차이만큼 빼줍니다
		}
		
		if (title == null) {//제목에 값이 없다면 페이징 정보만 있으면 됩니다
			Pageable pageable = PageRequest.of(page, pageSize, Direction.DESC, "insertDateTime");
			books = this.bookRepository.findAll(pageable).toList();
		}else {//제목이 비어있지 않다면 제목으로 검색하고 그결과에 페이징 정보를 제공 정방향(asc) 역방향(desc)
			Pageable pageable = PageRequest.of(page, pageSize);
			Sort sort = Sort.by(Order.desc("insertDateTime"));
			pageable.getSort().and(sort);//데이터를 있는 갯수만큼 (100) 
			books = this.bookRepository.findByTitleContains(title, pageable);
			//리파지토리에서 추가한 메소드를 이용해서 데이터를 호출합니다
		}
return books.stream().map(book -> new BookListResponseDTO(book.getBookId(), book.getTitle())).collect(Collectors.toList());
	}//생성자를 이용해서 응답객체를 만들어 냅니다
	
	
	
	
	
	
	
	
	
}
