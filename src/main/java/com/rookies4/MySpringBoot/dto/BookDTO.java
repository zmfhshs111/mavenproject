package com.rookies4.MySpringBoot.dto;

import com.rookies4.MySpringBoot.entity.Book;
import com.rookies4.MySpringBoot.entity.BookDetail;
import com.rookies4.MySpringBoot.entity.Publisher;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

// Lombok 어노테이션을 사용하여 Getter, Setter 등을 자동으로 생성할 수 있으나,
// 교육 목적으로 명시적으로 작성합니다.
public class BookDTO {

    /**
     * 도서 상세 정보 등록 및 수정을 위한 요청 DTO
     */
    public static class BookDetailRequest {
        private String description;
        private String language;
        @PositiveOrZero(message = "Page count must be positive or zero")
        private int pageCount;
        private String publisher; // BookDetail 내의 출판사 이름 필드
        private String coverImageUrl;
        private String edition;

        // Getters and Setters
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }
        public int getPageCount() { return pageCount; }
        public void setPageCount(int pageCount) { this.pageCount = pageCount; }
        public String getPublisher() { return publisher; }
        public void setPublisher(String publisher) { this.publisher = publisher; }
        public String getCoverImageUrl() { return coverImageUrl; }
        public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
        public String getEdition() { return edition; }
        public void setEdition(String edition) { this.edition = edition; }

        public BookDetail toEntity() {
            BookDetail detail = new BookDetail();
            detail.setDescription(this.description);
            detail.setLanguage(this.language);
            detail.setPageCount(this.pageCount);
            detail.setPublisher(this.publisher);
            detail.setCoverImageUrl(this.coverImageUrl);
            detail.setEdition(this.edition);
            return detail;
        }
    }

    /**
     * 신규 도서 생성을 위한 요청 DTO
     */
    public static class BookCreateRequest {
        @NotBlank(message = "Book title is required")
        private String title;

        @NotBlank(message = "Author name is required")
        private String author;

        @NotBlank(message = "ISBN is required")
        @Pattern(regexp = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$",
                message = "ISBN must be valid (10 or 13 digits, with or without hyphens)")
        private String isbn;

        @PastOrPresent(message = "Publish date cannot be in the future")
        private LocalDate publishDate;

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive or zero")
        private Integer price;

        @NotNull(message = "Publisher ID is required")
        private Long publisherId;

        @Valid // 중첩된 객체의 유효성 검사를 활성화합니다.
        private BookDetailRequest detailRequest;

        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }
        public LocalDate getPublishDate() { return publishDate; }
        public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }
        public Integer getPrice() { return price; }
        public void setPrice(Integer price) { this.price = price; }
        public Long getPublisherId() { return publisherId; }
        public void setPublisherId(Long publisherId) { this.publisherId = publisherId; }
        public BookDetailRequest getDetailRequest() { return detailRequest; }
        public void setDetailRequest(BookDetailRequest detailRequest) { this.detailRequest = detailRequest; }

        public Book toEntity(Publisher publisher) {
            Book book = new Book();
            book.setTitle(this.title);
            book.setAuthor(this.author);
            book.setIsbn(this.isbn);
            book.setPublishDate(this.publishDate);
            book.setPrice(this.price);
            book.setPublisher(publisher); // 연관관계 설정
            if (this.detailRequest != null) {
                book.setBookDetail(this.detailRequest.toEntity());
            }
            return book;
        }
    }

    /**
     * 도서 정보 전체 수정을 위한 요청 DTO (PUT)
     */
    public static class BookUpdateRequest {
        @NotBlank(message = "Book title is required")
        private String title;
        @NotBlank(message = "Author name is required")
        private String author;
        @NotBlank(message = "ISBN is required")
        @Pattern(regexp = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$",
                message = "ISBN must be valid (10 or 13 digits, with or without hyphens)")
        private String isbn;
        @PastOrPresent(message = "Publish date cannot be in the future")
        private LocalDate publishDate;
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive or zero")
        private Integer price;
        @NotNull(message = "Publisher ID is required")
        private Long publisherId;

        @Valid
        private BookDetailRequest detailRequest;

        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }
        public LocalDate getPublishDate() { return publishDate; }
        public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }
        public Integer getPrice() { return price; }
        public void setPrice(Integer price) { this.price = price; }
        public Long getPublisherId() { return publisherId; }
        public void setPublisherId(Long publisherId) { this.publisherId = publisherId; }
        public BookDetailRequest getDetailRequest() { return detailRequest; }
        public void setDetailRequest(BookDetailRequest detailRequest) { this.detailRequest = detailRequest; }
    }

    /**
     * 도서 상세 정보를 클라이언트에게 반환하기 위한 응답 DTO
     */
    public static class BookDetailResponse {
        private Long id;
        private String description;
        private String language;
        private int pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }
        public int getPageCount() { return pageCount; }
        public void setPageCount(int pageCount) { this.pageCount = pageCount; }
        public String getPublisher() { return publisher; }
        public void setPublisher(String publisher) { this.publisher = publisher; }
        public String getCoverImageUrl() { return coverImageUrl; }
        public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
        public String getEdition() { return edition; }
        public void setEdition(String edition) { this.edition = edition; }

        public static BookDetailResponse from(BookDetail bookDetail) {
            if (bookDetail == null) return null;
            BookDetailResponse response = new BookDetailResponse();
            response.setId(bookDetail.getId());
            response.setDescription(bookDetail.getDescription());
            response.setLanguage(bookDetail.getLanguage());
            response.setPageCount(bookDetail.getPageCount());
            response.setPublisher(bookDetail.getPublisher());
            response.setCoverImageUrl(bookDetail.getCoverImageUrl());
            response.setEdition(bookDetail.getEdition());
            return response;
        }
    }

    /**
     * 도서의 상세 정보(출판사, 도서상세 포함)를 반환하기 위한 응답 DTO
     */
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private LocalDate publishDate;
        private Integer price;
        private PublisherDTO.SimpleResponse publisher; // 출판사 정보 포함
        private BookDetailResponse detail; // 도서 상세 정보 이름 변경

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }
        public LocalDate getPublishDate() { return publishDate; }
        public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }
        public Integer getPrice() { return price; }
        public void setPrice(Integer price) { this.price = price; }
        public PublisherDTO.SimpleResponse getPublisher() { return publisher; }
        public void setPublisher(PublisherDTO.SimpleResponse publisher) { this.publisher = publisher; }
        public BookDetailResponse getDetail() { return detail; }
        public void setDetail(BookDetailResponse detail) { this.detail = detail; }

        public static BookResponse from(Book book) {
            BookResponse response = new BookResponse();
            response.setId(book.getId());
            response.setTitle(book.getTitle());
            response.setAuthor(book.getAuthor());
            response.setIsbn(book.getIsbn());
            response.setPublishDate(book.getPublishDate());
            response.setPrice(book.getPrice());
            if (book.getPublisher() != null) {
                // bookCount는 이 컨텍스트에서 필요 없으므로 0 또는 다른 기본값으로 설정
                response.setPublisher(PublisherDTO.SimpleResponse.from(book.getPublisher(), 0));
            }
            response.setDetail(BookDetailResponse.from(book.getBookDetail()));
            return response;
        }
    }

    /**
     * 출판사 정보 조회 시 내부에 포함될 간단한 도서 정보를 위한 응답 DTO
     */
    public static class SimpleBookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }

        public static SimpleBookResponse from(Book book) {
            SimpleBookResponse response = new SimpleBookResponse();
            response.setId(book.getId());
            response.setTitle(book.getTitle());
            response.setAuthor(book.getAuthor());
            response.setIsbn(book.getIsbn());
            return response;
        }
    }
}