package com.rookies4.MySpringBoot.repository;

import com.rookies4.MySpringBoot.entity.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
}
