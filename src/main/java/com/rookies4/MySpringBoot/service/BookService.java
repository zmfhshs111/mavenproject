package com.rookies4.MySpringBoot.service;

import com.rookies4.MySpringBoot.dto.BookDTO;
import com.rookies4.MySpringBoot.entity.Book;
import com.rookies4.MySpringBoot.entity.BookDetail;
import com.rookies4.MySpringBoot.exception.BusinessException;
import com.rookies4.MySpringBoot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException("ISBN already exists: " + request.getIsbn());
        }
        Book book = request.toEntity();
        Book savedBook = bookRepository.save(book);
        return BookDTO.BookResponse.from(savedBook);
    }

    @Transactional(readOnly = true)
    public List<BookDTO.BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.BookResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDTO.BookResponse getBookById(Long id) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException("Book not found with id: " + id));
        return BookDTO.BookResponse.from(book);
    }

    @Transactional(readOnly = true)
    public BookDTO.BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn).stream().findFirst()
                .orElseThrow(() -> new BusinessException("Book not found with isbn: " + isbn));
        return BookDTO.BookResponse.from(book);
    }

    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException("Book not found with id: " + id));

        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getIsbn() != null && !request.getIsbn().equals(book.getIsbn())) {
            if (bookRepository.existsByIsbn(request.getIsbn())) {
                throw new BusinessException("ISBN already exists: " + request.getIsbn());
            }
            book.setIsbn(request.getIsbn());
        }
        if (request.getPublishDate() != null) {
            book.setPublishDate(request.getPublishDate());
        }
        if (request.getPrice() != null) {
            book.setPrice(request.getPrice());
        }

        if (request.getDetailRequest() != null) {
            BookDetail detail = book.getBookDetail();
            if (detail == null) {
                detail = new BookDetail();
                book.setBookDetail(detail);
            }
            BookDTO.BookDetailRequest detailRequest = request.getDetailRequest();
            if(detailRequest.getDescription() != null) detail.setDescription(detailRequest.getDescription());
            if(detailRequest.getLanguage() != null) detail.setLanguage(detailRequest.getLanguage());
            if(detailRequest.getPublisher() != null) detail.setPublisher(detailRequest.getPublisher());
            if(detailRequest.getEdition() != null) detail.setEdition(detailRequest.getEdition());
            if(detailRequest.getCoverImageUrl() != null) detail.setCoverImageUrl(detailRequest.getCoverImageUrl());
            if(detailRequest.getPageCount() != 0) detail.setPageCount(detailRequest.getPageCount());
        }


        Book updatedBook = bookRepository.save(book);
        return BookDTO.BookResponse.from(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not found with id: " + id));
        bookRepository.delete(book);
    }
}
