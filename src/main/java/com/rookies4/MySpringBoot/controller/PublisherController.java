package com.rookies4.MySpringBoot.controller;

import com.rookies4.MySpringBoot.dto.PublisherDTO;
import com.rookies4.MySpringBoot.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping
    public ResponseEntity<PublisherDTO.Response> createPublisher(@Valid @RequestBody PublisherDTO.Request request) {
        PublisherDTO.Response createdPublisher = publisherService.createPublisher(request);
        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PublisherDTO.SimpleResponse> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO.Response> getPublisherById(@PathVariable Long id) {
        PublisherDTO.Response publisher = publisherService.getPublisherById(id);
        return ResponseEntity.ok(publisher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        publisherService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}