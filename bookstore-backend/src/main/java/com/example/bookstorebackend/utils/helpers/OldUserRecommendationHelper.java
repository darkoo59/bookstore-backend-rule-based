package com.example.bookstorebackend.utils.helpers;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.genre.Genre;
import com.example.bookstorebackend.order.model.Order;
import com.example.bookstorebackend.orderItem.OrderItem;
import com.example.bookstorebackend.person.model.Author;
import com.example.bookstorebackend.person.model.User;
import com.example.bookstorebackend.rating.model.Rating;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class OldUserRecommendationHelper {
    public static double calculateSimilarity(User user1, User user2) {
        List<Rating> ratings1 = user1.getRatings();
        List<Rating> ratings2 = user2.getRatings();

        List<String> commonBooks = findCommonBooks(ratings1, ratings2);
        double mean1 = calculateMeanRating(ratings1);
        double mean2 = calculateMeanRating(ratings2);
        double similarity = calculatePearsonCorrelation(ratings1, ratings2, commonBooks, mean1, mean2);
        System.out.println("Similarity (Pearson) = " + similarity);
        return similarity;
    }

    public static List<Book> collectBooksLikedByUser(User user) {
        List<Book> booksLikedByUser = new ArrayList<>();
        for(Rating rating : user.getRatings()) {
            if(rating.getRating() >= 4.0){
                booksLikedByUser.add(rating.getBook());
            }
        }
        return booksLikedByUser;
    }

    public static ConcurrentHashMap<Book,Integer> addBooksToHashMap(List<Book> books, ConcurrentHashMap<Book,Integer> recommendedBooksWithScores) {
        for (Book book:books) {
            System.out.println("Treba dodati " + book.getTitle());
            if (recommendedBooksWithScores.containsKey(book)) {
                // Book already exists, increment the score by 1
                Integer score = (Integer)recommendedBooksWithScores.get(book);
                recommendedBooksWithScores.put(book, score + 1);
            } else {
                // Book doesn't exist, add it with a score of 1
                recommendedBooksWithScores.put(book, 1);
            }
        }
        return recommendedBooksWithScores;
    }

    public static ArrayList<Book> addBooksToList(List<Book> booksLikedBySimilarUser, ArrayList<Book> recommendedBooks) {
        for (Book book:booksLikedBySimilarUser) {
            if(!recommendedBooks.contains(book))
                recommendedBooks.add(book);
        }
        return recommendedBooks;
    }

    public static Integer getNumOfAuthorBuys(Author author, List<Order> userOrders) {
        int numOfAuthorBuys = 0;
        for (Order order : userOrders) {
            if(order.isOrderInLastSixMonths()){
                for(OrderItem orderItem : order.getOrderItems()){
                    if(orderItem.getBook().getAuthor().getId().equals(author.getId()))
                        numOfAuthorBuys++;
                }
            }
        }
        return numOfAuthorBuys;
    }

    public static List<Genre> getGenresToRecommend(List<Genre> allGenres, List<Order> userOrders) {
        List<Genre> genresToRecommend = new ArrayList<>();
        for(Genre genre: allGenres) {
            int numOfGenreBuys = 0;
            int numOfBooksBuys = 0;
            for (Order orderIter : userOrders) {
                if(orderIter.isOrderInLastSixMonths()){
                    for(OrderItem orderItemIter : orderIter.getOrderItems()){
                        numOfBooksBuys++;
                        if(orderItemIter.getBook().getGenre().getId().equals(genre.getId()))
                            numOfGenreBuys++;
                    }
                }
            }
            if((double)numOfGenreBuys/numOfBooksBuys >= 0.3){
                genresToRecommend.add(genre);
            }
        }
        return genresToRecommend;
    }

    public static List<Book> getBooksByGenres(List<Genre> genresToAdd, List<Book> allBooks) {
        List<Book> booksToReturn = new ArrayList<>();
        for (Book book: allBooks) {
            if(genresToAdd.contains(book.getGenre()))
                booksToReturn.add(book);
        }
        return booksToReturn;
    }

    public static List<Book> extractAndSortBooksFromMap(ConcurrentHashMap<Book,Integer> recommendedBooksWithScores) {
        List<Map.Entry<Book, Integer>> list = new ArrayList<>(recommendedBooksWithScores.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Map<Book, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Book, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        List<Book> afterTopTwenty = sortedMap.entrySet().stream()
                .sorted((e1, e2) -> {
                    int scoreComparison = e2.getValue().compareTo(e1.getValue());

                    if (scoreComparison == 0) {
                        LocalDate date1 = e1.getKey().getDateOfAddingToBookstore();
                        LocalDate date2 = e2.getKey().getDateOfAddingToBookstore();
                        return date2.compareTo(date1);
                    }
                    return scoreComparison;
                })
                .limit(20)
                .map(Map.Entry::getKey)
                .toList();
        return afterTopTwenty;
    }

    public static List<Book> getSimilarBooks(List<Book> allBooks, List<Rating> userRatings) {
        List<Book> booksToReturn = new ArrayList<>();
        for (Book book : allBooks) {
            List<Rating> bookRatings = book.getRatings();
            List<Rating> overlappingRatings = userRatings.stream()
                    .filter(userRating -> bookRatings.stream()
                            .anyMatch(bookRating -> userRating.getUser().getId().equals(bookRating.getUser().getId())))
                    .collect(Collectors.toList());
            long commonRaters = overlappingRatings.stream()
                    .filter(userRating ->
                            bookRatings.stream()
                                    .anyMatch(bookRating ->
                                            Math.abs(userRating.getRating() - bookRating.getRating()) <= 1.0))
                    .count();
            double similarity = (double) commonRaters / (double) overlappingRatings.size();
            System.out.println("Similarity = " + similarity);
            if (similarity >= 0.7) {
                booksToReturn.add(book);
            }
        }
        return booksToReturn;
    }

    private static List<String> findCommonBooks(List<Rating> ratings1, List<Rating> ratings2) {
        return ratings1.stream()
                .map(Rating::getBook)
                .filter(book1 -> ratings2.stream().anyMatch(rating2 -> rating2.getBook().getId() == book1.getId()))
                .map(book -> book.getId().toString())
                .collect(Collectors.toList());
    }

    private static double calculateMeanRating(List<Rating> ratings) {
        double sum = 0.0;
        for (Rating rating : ratings) {
            sum += rating.getRating();
        }
        return sum / ratings.size();
    }

    private static double calculatePearsonCorrelation(List<Rating> ratings1, List<Rating> ratings2, List<String> commonBooks, double mean1, double mean2) {
        double numerator = 0.0;
        double denominator1 = 0.0;
        double denominator2 = 0.0;

        for (String bookId : commonBooks) {
            double rating1 = getRatingForBook(ratings1, bookId);
            double rating2 = getRatingForBook(ratings2, bookId);

            numerator += (rating1 - mean1) * (rating2 - mean2);
            denominator1 += Math.pow(rating1 - mean1, 2);
            denominator2 += Math.pow(rating2 - mean2, 2);
        }

        double similarity = numerator / (Math.sqrt(denominator1) * Math.sqrt(denominator2));
        return similarity;
    }

    private static double getRatingForBook(List<Rating> ratings, String bookId) {
        return ratings.stream()
                .filter(rating -> rating.getBook().getId().toString().equals(bookId))
                .findFirst()
                .map(Rating::getRating)
                .orElse(0.0);
    }
}
