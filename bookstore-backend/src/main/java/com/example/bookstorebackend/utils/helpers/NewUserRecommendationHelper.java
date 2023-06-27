package com.example.bookstorebackend.utils.helpers;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.person.model.Author;
import java.util.Comparator;
import java.util.List;

public class NewUserRecommendationHelper {
    public static boolean sortAuthors(List<Author> authors) {
        authors.sort(Comparator.comparingDouble(a -> ((Author)a).getTotalRatingNumber()).reversed());
        int endIndex = Math.min(authors.size(), 4);
        if (endIndex < authors.size()) {
            authors.subList(endIndex, authors.size()).clear();
        }
        return true;
    }

    public static List<Book> sortBooks(List<Book> books) {
        books.sort(Comparator.comparingDouble(a -> ((Book)a).getAverageRating()).reversed());
        return books.subList(0, Math.min(books.size(), 10));
    }
}
