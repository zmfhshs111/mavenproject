package com.rookies4.MySpringBoot.dto;

import com.rookies4.MySpringBoot.entity.Publisher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PublisherDTO {

    public static class Request {
        @NotBlank(message = "Publisher name is required")
        private String name;
        @PastOrPresent(message = "Established date cannot be in the future")
        private LocalDate establishedDate;
        private String address;

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public LocalDate getEstablishedDate() { return establishedDate; }
        public void setEstablishedDate(LocalDate establishedDate) { this.establishedDate = establishedDate; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }

    public static class Response {
        private Long id;
        private String name;
        private LocalDate establishedDate;
        private String address;
        private long bookCount;
        private List<BookDTO.SimpleBookResponse> books;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public LocalDate getEstablishedDate() { return establishedDate; }
        public void setEstablishedDate(LocalDate establishedDate) { this.establishedDate = establishedDate; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public long getBookCount() { return bookCount; }
        public void setBookCount(long bookCount) { this.bookCount = bookCount; }
        public List<BookDTO.SimpleBookResponse> getBooks() { return books; }
        public void setBooks(List<BookDTO.SimpleBookResponse> books) { this.books = books; }

        public static Response from(Publisher publisher) {
            Response response = new Response();
            response.setId(publisher.getId());
            response.setName(publisher.getName());
            response.setEstablishedDate(publisher.getEstablishedDate());
            response.setAddress(publisher.getAddress());
            response.setBookCount(publisher.getBooks().size());
            response.setBooks(publisher.getBooks().stream()
                    .map(BookDTO.SimpleBookResponse::from)
                    .collect(Collectors.toList()));
            return response;
        }
    }

    public static class SimpleResponse {
        private Long id;
        private String name;
        private LocalDate establishedDate;
        private String address;
        private long bookCount;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public LocalDate getEstablishedDate() { return establishedDate; }
        public void setEstablishedDate(LocalDate establishedDate) { this.establishedDate = establishedDate; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public long getBookCount() { return bookCount; }
        public void setBookCount(long bookCount) { this.bookCount = bookCount; }

        public static SimpleResponse from(Publisher publisher, long bookCount) {
            SimpleResponse response = new SimpleResponse();
            response.setId(publisher.getId());
            response.setName(publisher.getName());
            response.setEstablishedDate(publisher.getEstablishedDate());
            response.setAddress(publisher.getAddress());
            response.setBookCount(bookCount);
            return response;
        }
    }
}