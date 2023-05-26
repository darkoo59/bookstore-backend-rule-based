package com.example.bookstorebackend.rating.repository;

import com.example.bookstorebackend.rating.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query(value = "SELECT * FROM Rating WHERE :bookId = book_id", nativeQuery = true)
    List<Rating> getByBookId(@Param("bookId") Long bookId);

    @Query(value = "SELECT * FROM Rating WHERE :userId = user_id", nativeQuery = true)
    List<Rating> getByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "DELETE FROM Rating WHERE user_id = :userId AND book_id = :bookId", nativeQuery = true)
    void deleteByUserIdAndBookId(@Param("userId") Long userId, @Param("bookId") Long bookId);
}
