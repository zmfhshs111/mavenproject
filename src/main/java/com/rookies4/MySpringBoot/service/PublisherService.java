package com.rookies4.MySpringBoot.service;

import com.rookies4.MySpringBoot.dto.PublisherDTO;
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
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<PublisherDTO.SimpleResponse> getAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(p -> PublisherDTO.SimpleResponse.from(p, bookRepository.countByPublisherId(p.getId())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PublisherDTO.Response getPublisherById(Long id) {
        Publisher publisher = publisherRepository.findByIdWithBooks(id)
                .orElseThrow(() -> new BusinessException("Publisher not found with id: " + id));
        return PublisherDTO.Response.from(publisher);
    }

    @Transactional
    public PublisherDTO.Response createPublisher(PublisherDTO.Request request) {
        if (publisherRepository.existsByName(request.getName())) {
            throw new BusinessException("Publisher already exists with name: " + request.getName());
        }
        Publisher publisher = new Publisher();
        publisher.setName(request.getName());
        publisher.setEstablishedDate(request.getEstablishedDate());
        publisher.setAddress(request.getAddress());

        Publisher savedPublisher = publisherRepository.save(publisher);
        return PublisherDTO.Response.from(savedPublisher);
    }

    @Transactional
    public void deletePublisher(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Publisher not found with id: " + id));

        if (!publisher.getBooks().isEmpty()) {
            throw new BusinessException("Cannot delete publisher with id: " + id + ". It has " + publisher.getBooks().size() + " books");
        }
        publisherRepository.delete(publisher);
    }

    // ... 나머지 update, getByName 등 메소드 추가 ...
}