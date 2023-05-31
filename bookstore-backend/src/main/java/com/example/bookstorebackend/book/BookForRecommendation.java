package com.example.bookstorebackend.book;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookForRecommendation {
    private Book book;
    private int recommendationPoints;
}
