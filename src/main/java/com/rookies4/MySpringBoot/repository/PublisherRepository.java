package com.rookies4.MySpringBoot.repository;

import com.rookies4.MySpringBoot.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findByName(String name);

    @Query("SELECT p FROM Publisher p LEFT JOIN FETCH p.books WHERE p.id = :id")
    Optional<Publisher> findByIdWithBooks(@Param("id") Long id);

    boolean existsByName(String name);
}