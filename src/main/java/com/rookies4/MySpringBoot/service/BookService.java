package com.rookies4.MySpringBoot.service;

import com.rookies4.MySpringBoot.dto.BookDTO;
import com.rookies4.MySpringBoot.entity.Book;
import com.rookies4.MySpringBoot.entity.BookDetail;
import com.rookies4.MySpringBoot.entity.Publisher;
import com.rookies4.MySpringBoot.exception.BusinessException;
import com.rookies4.MySpringBoot.repository.BookRepository;
import com.rookies4.MySpringBoot.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException("Book already exists with ISBN: " + request.getIsbn());
        }
        Publisher publisher = publisherRepository.findById(request.getPublisherId())
                .orElseThrow(() -> new BusinessException("Publisher not found with id: " + request.getPublisherId()));

        Book book = request.toEntity(publisher);
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
        Book book = bookRepository.findByIdWithAllDetails(id)
                .orElseThrow(() -> new BusinessException("Book not found with id: " + id));
        return BookDTO.BookResponse.from(book);
    }

    @Transactional(readOnly = true)
    public BookDTO.BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbnWithBookDetail(isbn)
                .orElseThrow(() -> new BusinessException("Book not found with isbn: " + isbn));
        return BookDTO.BookResponse.from(book);
    }

    @Transactional(readOnly = true)
    public List<BookDTO.BookResponse> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(BookDTO.BookResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookDTO.BookResponse> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(BookDTO.BookResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book book = bookRepository.findByIdWithAllDetails(id)
                .orElseThrow(() -> new BusinessException("Book not found with id: " + id));

        // ISBN을 변경하는 경우 중복 체크
        if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException("Book already exists with ISBN: " + request.getIsbn());
        }

        // 출판사를 변경하는 경우, 존재하는 출판사인지 확인
        Publisher publisher = publisherRepository.findById(request.getPublisherId())
                .orElseThrow(() -> new BusinessException("Publisher not found with id: " + request.getPublisherId()));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublishDate(request.getPublishDate());
        book.setPrice(request.getPrice());
        book.setPublisher(publisher);

        if (request.getDetailRequest() != null) {
            BookDetail detail = book.getBookDetail();
            if (detail == null) {
                detail = new BookDetail();
                book.setBookDetail(detail);
            }
            BookDTO.BookDetailRequest detailRequest = request.getDetailRequest();
            detail.setDescription(detailRequest.getDescription());
            detail.setLanguage(detailRequest.getLanguage());
            detail.setPublisher(detailRequest.getPublisher());
            detail.setEdition(detailRequest.getEdition());
            detail.setCoverImageUrl(detailRequest.getCoverImageUrl());
            detail.setPageCount(detailRequest.getPageCount());
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