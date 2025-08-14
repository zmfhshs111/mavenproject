package com.rookies4.MySpringBoot.repository;

import com.rookies4.MySpringBoot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByIsbn(String isbn);

    List<Book> findByAuthor(String author);
}
