package com.example.bookstorebackend.rating.controller;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.order.OrderService;
import com.example.bookstorebackend.order.dto.OrderDTO;
import com.example.bookstorebackend.rating.dto.RatingDTO;
import com.example.bookstorebackend.rating.model.Rating;
import com.example.bookstorebackend.rating.service.RatingService;
import com.example.bookstorebackend.security.filter.AuthUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/bookstore/rating")
public class RatingController {

    private final RatingService ratingService;
    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    @PostMapping("/rate")
    @Secured("ROLE_USER")
    public ResponseEntity<?> MakeRating(@RequestBody RatingDTO rating, HttpServletRequest request) {
        try {
            ratingService.makeRating(rating, AuthUtility.getEmailFromRequest(request));
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unknown error", BAD_REQUEST);
        }
    }

    @GetMapping("/user-ratings")
    @Secured("ROLE_USER")
    public ResponseEntity<List<RatingDTO>> getAllForUser(HttpServletRequest request){
        return new ResponseEntity<>(ratingService.getAllForUser(AuthUtility.getEmailFromRequest(request)), OK);
    }
    @GetMapping("/number-of-ratings")
    @Secured("ROLE_USER")
    public ResponseEntity<Integer> getNumberOfRatings(HttpServletRequest request){
        return new ResponseEntity<>(ratingService.getNumberOfRatingsForUser(AuthUtility.getEmailFromRequest(request)), OK);
    }
}
