package com.example.bookstorebackend.rating.service;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.book.BookService;
import com.example.bookstorebackend.person.repository.AdminRepository;
import com.example.bookstorebackend.person.service.UserService;
import com.example.bookstorebackend.rating.dto.RatingDTO;
import com.example.bookstorebackend.rating.model.Rating;
import com.example.bookstorebackend.rating.repository.RatingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserService userService;
    private final BookService bookService;

    public void makeRating(RatingDTO rating, String emailFromRequest) {
        Rating newRating = new Rating();
        newRating.setRating(rating.getRating());
        newRating.setUser(userService.getUser(emailFromRequest));
        newRating.setBook(bookService.getById(rating.getBookId()));
        ratingRepository.save(newRating);
        Book bookToUpdate = bookService.getById(rating.getBookId());
        bookToUpdate.setAverageRating(getBookAverageRating(rating.getBookId()));
        bookService.save(bookToUpdate);
    }

    public double getBookAverageRating(Long bookId){
        List<Rating> allRatings = ratingRepository.getByBookId(bookId);
        double averageRating = 0.0;
        if(!allRatings.isEmpty()){
            for (Rating rating:allRatings) {
                averageRating += rating.getRating();
            }
            return averageRating/allRatings.size();
        }
        return averageRating;
    }
}
