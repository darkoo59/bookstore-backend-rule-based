package com.example.bookstorebackend.book;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCharacteristics {
    private Book book;
    private boolean isNew;
    private boolean isPopular;
    private boolean goodRated;
    private boolean badRated;
    private boolean suggested;

    public BookCharacteristics(Book book) {
        this.book = book;
    }
}
