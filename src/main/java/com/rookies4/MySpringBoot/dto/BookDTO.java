package com.rookies4.MySpringBoot.dto;

import com.rookies4.MySpringBoot.entity.Book;
import com.rookies4.MySpringBoot.entity.BookDetail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public class BookDTO {

    public static class BookDetailRequest {
        private String description;
        private String language;
        private int pageCount;
        private String publisher;
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

    public static class BookCreateRequest {
        @NotBlank(message = "Title is mandatory")
        private String title;
        @NotBlank(message = "Author is mandatory")
        private String author;
        @NotBlank(message = "ISBN is mandatory")
        private String isbn;
        private LocalDate publishDate;
        @NotNull(message = "Price is mandatory")
        @Positive(message = "Price must be positive")
        private Integer price;

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
        public BookDetailRequest getDetailRequest() { return detailRequest; }
        public void setDetailRequest(BookDetailRequest detailRequest) { this.detailRequest = detailRequest; }

        public Book toEntity() {
            Book book = new Book();
            book.setTitle(this.title);
            book.setAuthor(this.author);
            book.setIsbn(this.isbn);
            book.setPublishDate(this.publishDate);
            book.setPrice(this.price);
            if (this.detailRequest != null) {
                book.setBookDetail(this.detailRequest.toEntity());
            }
            return book;
        }
    }

    public static class BookUpdateRequest {
        private String title;
        private String author;
        private String isbn;
        private LocalDate publishDate;
        @Positive(message = "Price must be positive")
        private Integer price;

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
        public BookDetailRequest getDetailRequest() { return detailRequest; }
        public void setDetailRequest(BookDetailRequest detailRequest) { this.detailRequest = detailRequest; }
    }
    
    public static class BookDetailResponse {
        private String description;
        private String language;
        private int pageCount;
        private String publisher;
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

        public static BookDetailResponse from(BookDetail bookDetail) {
            if (bookDetail == null) return null;
            BookDetailResponse response = new BookDetailResponse();
            response.setDescription(bookDetail.getDescription());
            response.setLanguage(bookDetail.getLanguage());
            response.setPageCount(bookDetail.getPageCount());
            response.setPublisher(bookDetail.getPublisher());
            response.setCoverImageUrl(bookDetail.getCoverImageUrl());
            response.setEdition(bookDetail.getEdition());
            return response;
        }
    }

    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private LocalDate publishDate;
        private Integer price;
        private BookDetailResponse bookDetail;

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
        public BookDetailResponse getBookDetail() { return bookDetail; }
        public void setBookDetail(BookDetailResponse bookDetail) { this.bookDetail = bookDetail; }

        public static BookResponse from(Book book) {
            BookResponse response = new BookResponse();
            response.setId(book.getId());
            response.setTitle(book.getTitle());
            response.setAuthor(book.getAuthor());
            response.setIsbn(book.getIsbn());
            response.setPublishDate(book.getPublishDate());
            response.setPrice(book.getPrice());
            response.setBookDetail(BookDetailResponse.from(book.getBookDetail()));
            return response;
        }
    }
}
