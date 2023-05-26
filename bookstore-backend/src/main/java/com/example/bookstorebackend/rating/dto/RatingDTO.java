package com.example.bookstorebackend.rating.dto;

import com.example.bookstorebackend.person.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    private Long id;
    private Long bookId;
    private double rating;
    private UserDTO user;
}
