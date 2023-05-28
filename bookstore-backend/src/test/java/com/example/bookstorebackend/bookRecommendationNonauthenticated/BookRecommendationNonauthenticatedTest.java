package com.example.bookstorebackend.bookRecommendationNonauthenticated;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.book.BookCharacteristics;
import com.example.bookstorebackend.rating.model.Rating;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class BookRecommendationNonauthenticatedTest {
    private KieSession kieSession;
    private static final String nonauthBooksDrlFile = "rules/booksNonauthenticated/nonauth_books.drl";

    @Before
    public void setUp() throws Exception {
        KieServices kieServices = KieServices.Factory.get();

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(nonauthBooksDrlFile));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();

        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        kieSession = kieContainer.newKieSession();
    }

    @Test
    public void whenBookIsAddedInLastMonth_BookIsNew() {
        Book book = new Book();
        book.setRatings(new ArrayList<Rating>());
        book.setDateOfAddingToBookstore(LocalDate.now().minusDays(15));
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(book);
        kieSession.insert(bc);

        kieSession.fireAllRules();
        kieSession.dispose();

        assertTrue(bc.isNew());
    }

    @Test
    public void whenBookIsPublishedInLastSixMonths_BookIsNew() {
        Book book = new Book();
        book.setRatings(new ArrayList<Rating>());
        book.setPublishDate(LocalDate.now().minusMonths(4));
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(book);
        kieSession.insert(bc);

        kieSession.fireAllRules();
        kieSession.dispose();

        assertTrue(bc.isNew());
    }

    @Test
    public void whenBookIsNew_SuggestIt() {
        Book b = new Book();
        b.setRatings(new ArrayList<Rating>());
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(b);
        bc.setNew(true);
        kieSession.insert(bc);

        kieSession.fireAllRules();
        kieSession.dispose();

        assertTrue(bc.isSuggested());
    }

    @Test
    public void whenBookHasAvgGradeEqualOrHigherThenFour_ItsGoodRated() {
        Book b = new Book();
        b.setRatings(new ArrayList<Rating>());
        b.setAverageRating(4.5);
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(b);
        kieSession.insert(bc);

        kieSession.fireAllRules();
        kieSession.dispose();

        assertTrue(bc.isGoodRated());
    }

    @Test
    public void whenBookHasAvgGradeLowerOrEqualThenTwo_ItsBadRated() {
        Book b = new Book();
        b.setRatings(new ArrayList<Rating>());
        b.setAverageRating(1.5);
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(b);
        kieSession.insert(bc);

        kieSession.fireAllRules();
        kieSession.dispose();

        assertTrue(bc.isBadRated());
    }

    @Test
    public void whenBookHasAvgGradeBetweenTwoPointFiveAndFour_ItsNeutralRated() {
        Book b = new Book();
        b.setRatings(new ArrayList<Rating>());
        b.setAverageRating(2.7);
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(b);
        kieSession.insert(bc);

        kieSession.fireAllRules();
        kieSession.dispose();

        assertFalse(bc.isGoodRated());
        assertFalse(bc.isBadRated());
    }

    @Test
    public void whenBookDoesntHaveAvgGrade_ItsNeutralRated() {
        Book b = new Book();
        b.setRatings(new ArrayList<Rating>());
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(b);
        kieSession.insert(bc);

        kieSession.fireAllRules();
        kieSession.dispose();

        assertFalse(bc.isGoodRated());
        assertFalse(bc.isBadRated());
    }

    @Test
    public void whenBookHasTwentyOrMoreRatings_ItsPopular() {
        Book b = new Book();
        ArrayList<Rating> ratingList = new ArrayList<>();
        for(int i = 1; i < 25; i++) {
            ratingList.add(new Rating((long) i, b, i % 5,null));
        }
        b.setRatings(ratingList);
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(b);
        kieSession.insert(bc);

        kieSession.fireAllRules();
        kieSession.dispose();

        assertTrue(bc.isPopular());
    }

    @Test
    public void whenBookHasTenOrMoreRatingsAndItsNew_ItsPopular() {
        Book b = new Book();
        ArrayList<Rating> ratingList = new ArrayList<>();
        for(int i = 1; i < 11; i++) {
            ratingList.add(new Rating((long) i, b, (i * 2) % 5,null));
        }
        b.setRatings(ratingList);
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(b);
        bc.setNew(true);
        kieSession.insert(bc);

        kieSession.fireAllRules();
        kieSession.dispose();

        assertTrue(bc.isPopular());
    }

    @Test
    public void whenBookIsPopularAndItsRatedGood_SuggestIt() {
        Book b = new Book();
        ArrayList<Rating> ratingList = new ArrayList<>();
        b.setRatings(ratingList);
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(b);
        bc.setPopular(true);
        bc.setGoodRated(true);
        kieSession.insert(bc);

        kieSession.fireAllRules();
        kieSession.dispose();

        assertTrue(bc.isSuggested());
    }

    @Test
    public void whenBookIsPopularAndItsRatedNeutral_SuggestIt() {
        Book b = new Book();
        ArrayList<Rating> ratingList = new ArrayList<>();
        b.setRatings(ratingList);
        BookCharacteristics bc = new BookCharacteristics();
        bc.setBook(b);
        bc.setPopular(true);
        bc.setGoodRated(false);
        bc.setBadRated(false);
        kieSession.insert(bc);

        kieSession.fireAllRules();
        kieSession.dispose();

        assertTrue(bc.isSuggested());
    }

    @Test
    public void whenBookSuggestionListLargerThenTen_BadRatedAreRemoved() {
        ArrayList<BookCharacteristics> bookCharacteristicsList = new ArrayList<>();
        int expectedSize = 9;

        for(int i = 0; i < 13; i++) {
            Book b = new Book();
            b.setRatings(new ArrayList<Rating>());
            BookCharacteristics bc = new BookCharacteristics(b);
            bookCharacteristicsList.add(bc);
            bc.setSuggested(true);
            bc.setBadRated(i % 4 == 0);
            kieSession.insert(bc);
            kieSession.fireAllRules();
        }
        kieSession.getAgenda().getAgendaGroup("remove books").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();

        assertEquals(expectedSize, bookCharacteristicsList
                                    .stream()
                                    .filter(BookCharacteristics::isSuggested)
                                    .toList()
                                    .size());
    }

    @Test
    public void whenBookSuggestionListLargerThenTenAfterRemovingBadRated_TakeTenBooksRandomly() {
        ArrayList<BookCharacteristics> bookCharacteristicsList = new ArrayList<>();
        int expectedSize = 10;

        for(int i = 0; i < 17; i++) {
            Book b = new Book();
            b.setRatings(new ArrayList<Rating>());
            BookCharacteristics bc = new BookCharacteristics(b);
            bookCharacteristicsList.add(bc);
            bc.setSuggested(true);
            bc.setBadRated(i % 6 == 0);
            kieSession.insert(bc);
            kieSession.fireAllRules();
        }
        kieSession.getAgenda().getAgendaGroup("remove books").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();

        assertEquals(expectedSize, bookCharacteristicsList
                                    .stream()
                                    .filter(BookCharacteristics::isSuggested)
                                    .toList()
                                    .size());
    }

    @Test
    public void whenAllRulesTriggered_validInput_validOutput() {
        List<BookCharacteristicsWithExpectedValue> bookCharacteristicWithExpectedValueList = getFakeBookCharacteristicsWithExpectedValue();

        for (BookCharacteristicsWithExpectedValue bcwex : bookCharacteristicWithExpectedValueList) {
            kieSession.insert(bcwex.bookCharacteristics);
            kieSession.fireAllRules();
            Assert.assertTrue(areBookCharacteristicsSame(bcwex.bookCharacteristics, bcwex.expectedBookCharacteristics));
        }

        kieSession.dispose();
    }

    private List<BookCharacteristicsWithExpectedValue> getFakeBookCharacteristicsWithExpectedValue() {
        List<BookCharacteristicsWithExpectedValue> bookCharacteristicsWithExpectedValueList = new ArrayList<>();

        Book b1 = new Book();
        b1.setDateOfAddingToBookstore(LocalDate.of(2022, 11, 22));
        b1.setPublishDate(LocalDate.now().minusMonths(3));
        b1.setAverageRating(4.5);
        b1.setRatings(new ArrayList<>());
        BookCharacteristics bc1 = new BookCharacteristics(b1);
        BookCharacteristics bc1Expected = new BookCharacteristics(b1);
        bc1Expected.setNew(true);
        bc1Expected.setGoodRated(true);
        bc1Expected.setSuggested(true);
        BookCharacteristicsWithExpectedValue bcwex1 = new BookCharacteristicsWithExpectedValue(bc1, bc1Expected);
        bookCharacteristicsWithExpectedValueList.add(bcwex1);

        Book b2 = new Book();
        b2.setDateOfAddingToBookstore(LocalDate.now().minusDays(20));
        b2.setPublishDate(LocalDate.of(1999, 3, 10));
        b2.setRatings(new ArrayList<>());
        BookCharacteristics bc2 = new BookCharacteristics(b2);
        BookCharacteristics bc2Expected = new BookCharacteristics(b2);
        bc2Expected.setNew(true);
        bc2Expected.setSuggested(true);
        BookCharacteristicsWithExpectedValue bcwex2 = new BookCharacteristicsWithExpectedValue(bc2, bc2Expected);
        bookCharacteristicsWithExpectedValueList.add(bcwex2);

        Book b3 = new Book();
        b3.setDateOfAddingToBookstore(LocalDate.of(2022, 5, 14));
        b3.setPublishDate(LocalDate.of(2022, 6, 30));
        b3.setAverageRating(1.5);
        b3.setRatings(new ArrayList<>());
        BookCharacteristics bc3 = new BookCharacteristics(b3);
        BookCharacteristics bc3Expected = new BookCharacteristics(b3);
        bc3Expected.setBadRated(true);
        BookCharacteristicsWithExpectedValue bcwex3 = new BookCharacteristicsWithExpectedValue(bc3, bc3Expected);
        bookCharacteristicsWithExpectedValueList.add(bcwex3);

        Book b4 = new Book();
        b4.setDateOfAddingToBookstore(LocalDate.of(2021, 1, 14));
        b4.setPublishDate(LocalDate.of(2021, 7, 20));
        b4.setAverageRating(3.0);
        b4.setRatings(new ArrayList<>());
        BookCharacteristics bc4 = new BookCharacteristics(b4);
        BookCharacteristics bc4Expected = new BookCharacteristics(b4);
        BookCharacteristicsWithExpectedValue bcwex4 = new BookCharacteristicsWithExpectedValue(bc4, bc4Expected);
        bookCharacteristicsWithExpectedValueList.add(bcwex4);

        Book b5 = new Book();
        b5.setDateOfAddingToBookstore(LocalDate.of(2021, 1, 14));
        b5.setPublishDate(LocalDate.of(2021, 7, 20));
        b5.setAverageRating(4.8);
        ArrayList<Rating> ratingList5 = new ArrayList<>();
        for(int i = 1; i < 22; i++) {
            ratingList5.add(new Rating((long) i, b5, i % 5,null));
        }
        b5.setRatings(ratingList5);
        BookCharacteristics bc5 = new BookCharacteristics(b5);
        BookCharacteristics bc5Expected = new BookCharacteristics(b5);
        bc5Expected.setGoodRated(true);
        bc5Expected.setPopular(true);
        bc5Expected.setSuggested(true);
        BookCharacteristicsWithExpectedValue bcwex5 = new BookCharacteristicsWithExpectedValue(bc5, bc5Expected);
        bookCharacteristicsWithExpectedValueList.add(bcwex5);

        Book b6 = new Book();
        b6.setDateOfAddingToBookstore(LocalDate.now().minusDays(22));
        b6.setPublishDate(LocalDate.of(2021, 7, 20));
        b6.setAverageRating(3.0);
        ArrayList<Rating> ratingList6 = new ArrayList<>();
        for(int i = 1; i < 12; i++) {
            ratingList6.add(new Rating((long) i, b6, (i * 2) % 5,null));
        }
        b6.setRatings(ratingList6);
        BookCharacteristics bc6 = new BookCharacteristics(b6);
        BookCharacteristics bc6Expected = new BookCharacteristics(b6);
        bc6Expected.setNew(true);
        bc6Expected.setPopular(true);
        bc6Expected.setSuggested(true);
        BookCharacteristicsWithExpectedValue bcwex6 = new BookCharacteristicsWithExpectedValue(bc6, bc6Expected);
        bookCharacteristicsWithExpectedValueList.add(bcwex6);

        return bookCharacteristicsWithExpectedValueList;
    }

    private boolean areBookCharacteristicsSame(BookCharacteristics bc1, BookCharacteristics bc2) {
        return bc1.isNew() == bc2.isNew() &&
                bc1.isSuggested() == bc2.isSuggested() &&
                bc1.isPopular() == bc2.isPopular() &&
                bc1.isBadRated() == bc2.isBadRated() &&
                bc1.isGoodRated() == bc2.isGoodRated();
    }


    static class BookCharacteristicsWithExpectedValue {
        public BookCharacteristics bookCharacteristics;
        public BookCharacteristics expectedBookCharacteristics;

        public BookCharacteristicsWithExpectedValue(BookCharacteristics bc, BookCharacteristics bcExpected) {
            bookCharacteristics = bc;
            expectedBookCharacteristics = bcExpected;
        }
    }

}


