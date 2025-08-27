package com.rookies4.MySpringBoot.controller;

import com.rookies4.MySpringBoot.dto.BookDTO;
import com.rookies4.MySpringBoot.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
// 프론트엔드(Live Server)에서의 요청을 허용하기 위해 CORS 설정 추가
// 개발 중에는 모든 출처(*)를 허용하고, 배포 시에는 특정 도메인으로 변경하는 것이 안전합니다.
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 새 도서를 등록합니다.
     * @param request 도서 생성 정보 DTO
     * @return 생성된 도서 정보
     */
    @PostMapping
    public ResponseEntity<BookDTO.BookResponse> createBook(@Valid @RequestBody BookDTO.BookCreateRequest request) {
        BookDTO.BookResponse createdBook = bookService.createBook(request);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    /**
     * 모든 도서 목록을 조회합니다.
     * @return 도서 목록
     */
    @GetMapping
    public List<BookDTO.BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }

    /**
     * ID로 특정 도서를 조회합니다.
     * @param id 조회할 도서의 ID
     * @return 특정 도서 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> getBookById(@PathVariable Long id) {
        BookDTO.BookResponse book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    /**
     * ISBN으로 특정 도서를 조회합니다.
     * @param isbn 조회할 도서의 ISBN
     * @return 특정 도서 정보
     */
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.BookResponse> getBookByIsbn(@PathVariable String isbn) {
        BookDTO.BookResponse book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    /**
     * 저자 이름으로 도서를 검색합니다. (부분 일치)
     * @param author 검색할 저자 이름
     * @return 검색된 도서 목록
     */
    @GetMapping("/search/author")
    public List<BookDTO.BookResponse> getBooksByAuthor(@RequestParam String author) {
        return bookService.getBooksByAuthor(author);
    }

    /**
     * 도서 제목으로 도서를 검색합니다. (부분 일치)
     * @param title 검색할 도서 제목
     * @return 검색된 도서 목록
     */
    @GetMapping("/search/title")
    public List<BookDTO.BookResponse> getBooksByTitle(@RequestParam String title) {
        return bookService.getBooksByTitle(title);
    }


    /**
     * 기존 도서 정보를 수정합니다.
     * @param id 수정할 도서의 ID
     * @param request 수정할 도서 정보 DTO
     * @return 수정된 도서 정보
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO.BookResponse> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO.BookUpdateRequest request) {
        BookDTO.BookResponse updatedBook = bookService.updateBook(id, request);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * 특정 도서를 삭제합니다.
     * @param id 삭제할 도서의 ID
     * @return 응답 없음 (성공 시 204 No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}