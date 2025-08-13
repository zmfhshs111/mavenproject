package com.rookies4.MySpringBoot;

import com.rookies4.MySpringBoot.book.BookRepository;
import com.rookies4.MySpringBoot.book.Book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testCreateBook() {
        Book book = new Book();
        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setIsbn("9788956746425");
        book.setPublishDate(LocalDate.of(2025, 5, 7));
        book.setPrice(30000);

        Book savedBook = bookRepository.save(book);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isGreaterThan(0);
    }

    @Test
    void testFindByIsbn() {
        testCreateBook(); // Create a book first
        List<Book> books = bookRepository.findByIsbn("9788956746425");
        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getTitle()).isEqualTo("스프링 부트 입문");
    }

    @Test
    void testFindByAuthor() {
        testCreateBook(); // Create a book first
        List<Book> books = bookRepository.findByAuthor("홍길동");
        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getTitle()).isEqualTo("스프링 부트 입문");
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setIsbn("9788956746425");
        book.setPublishDate(LocalDate.of(2025, 5, 7));
        book.setPrice(30000);
        Book savedBook = bookRepository.save(book);

        savedBook.setPrice(35000);
        Book updatedBook = bookRepository.save(savedBook);

        assertThat(updatedBook.getPrice()).isEqualTo(35000);
    }

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setIsbn("9788956746425");
        book.setPublishDate(LocalDate.of(2025, 5, 7));
        book.setPrice(30000);
        Book savedBook = bookRepository.save(book);

        bookRepository.deleteById(savedBook.getId());

        assertThat(bookRepository.findById(savedBook.getId())).isEmpty();
    }
}
