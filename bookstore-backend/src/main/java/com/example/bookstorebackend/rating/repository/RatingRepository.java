package com.example.bookstorebackend.rating.repository;

import com.example.bookstorebackend.rating.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query(value = "SELECT * FROM Rating WHERE :bookId = book_id", nativeQuery = true)
    List<Rating> getByBookId(@Param("bookId") Long bookId);
}
