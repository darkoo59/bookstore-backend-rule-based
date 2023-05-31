package com.example.bookstorebackend.utils;

import com.example.bookstorebackend.book.Book;
import com.example.bookstorebackend.book.dto.BookDTO;
import com.example.bookstorebackend.genre.Genre;
import com.example.bookstorebackend.genre.dto.GenreDTO;
import com.example.bookstorebackend.person.dto.AuthorDTO;
import com.example.bookstorebackend.person.dto.RegisterDTO;
import com.example.bookstorebackend.person.dto.UserDTO;
import com.example.bookstorebackend.person.model.Author;
import com.example.bookstorebackend.person.model.User;
import com.example.bookstorebackend.rating.dto.RatingDTO;
import com.example.bookstorebackend.rating.model.Rating;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.ArrayList;
import java.util.List;

public class ObjectsMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static User convertRegisterDTOToUser(RegisterDTO registerDTO) {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<RegisterDTO, User> answerMap = new PropertyMap<>() {
            protected void configure() {
                skip(destination.getId());
                map().setFirstname(source.getFirstname());
                map().setLastname(source.getLastname());
                map().setEmail(source.getEmail());
                map().setPassword(source.getPassword());
                map().setPhone(source.getPhone());
                map().setNationalId(source.getNationalId());
                map().setGender(source.getGender());
                map().setOccupation(source.getOccupation());
                map().setInformation(source.getInformation());
            }
        };

        modelMapper.addMappings(answerMap);
        return modelMapper.map(registerDTO, User.class);
    }

    public static BookDTO convertBookToDTO(Book book) {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Book, BookDTO> answerMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setAuthor(convertAuthorToDTO(source.getAuthor()));
                map().setPrice(source.getPrice());
                map().setTitle(source.getTitle());
                map().setNumberOfPages(source.getNumberOfPages());
                map().setPublisher(source.getPublisher());
                map().setRatings(convertRatingsToDTOs(source.getRatings()));
                map().setGenre(convertGenreToDTO(source.getGenre()));
                map().setAverageRating(source.getAverageRating());
            }
        };
        modelMapper.addMappings(answerMap);
        return modelMapper.map(book, BookDTO.class);
    }

    public static List<BookDTO> convertBooksToDTOs(List<Book> books){
        List<BookDTO> list = new ArrayList<>();
        if(books == null)
            return list;
        for(Book book: books)
            list.add(convertBookToDTO(book));
        return list;
    }

    public static GenreDTO convertGenreToDTO(Genre genre){
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Genre, GenreDTO> answerMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
            }
        };
        modelMapper.addMappings(answerMap);
        return modelMapper.map(genre, GenreDTO.class);
    }

    public static List<GenreDTO> convertGenresToDTOs(List<Genre> genres){
        List<GenreDTO> list = new ArrayList<>();
        if(genres == null)
            return list;
        for(Genre genre: genres)
            list.add(convertGenreToDTO(genre));
        return list;
    }

    public static AuthorDTO convertAuthorToDTO(Author author){
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Author, AuthorDTO> answerMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
            }
        };
        modelMapper.addMappings(answerMap);
        return modelMapper.map(author, AuthorDTO.class);
    }

    public static RatingDTO convertRatingToDTO(Rating rating){
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Rating, RatingDTO> answerMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setRating(source.getRating());
                map().setUser(convertUserToDTO(source.getUser()));
                map().setBookId(source.getBook().getId());
            }
        };
        modelMapper.addMappings(answerMap);
        return modelMapper.map(rating, RatingDTO.class);
    }

    public static List<RatingDTO> convertRatingsToDTOs(List<Rating> ratings){
        List<RatingDTO> list = new ArrayList<>();
        if(ratings == null)
            return list;
        for(Rating rating: ratings)
            list.add(convertRatingToDTO(rating));
        return list;
    }

    public static UserDTO convertUserToDTO(User user){
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<User, UserDTO> answerMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setFirstname(source.getFirstname());
                map().setLastname(source.getLastname());
                map().setEmail(source.getEmail());
                map().setGenres(convertGenresToDTOs(source.getFavouriteGenres()));
            }
        };
        modelMapper.addMappings(answerMap);
        return modelMapper.map(user, UserDTO.class);
    }
    
    public static List<RatingDTO> convertRatingsToRatingDTO(List<Rating> ratings){
        List<RatingDTO> ratingsToReturn = new ArrayList<>();
        for (Rating rating: ratings) {
            RatingDTO ratingDTO = new RatingDTO();
            ratingDTO.setRating(rating.getRating());
            ratingDTO.setBookId(rating.getBook().getId());
            ratingsToReturn.add(ratingDTO);
        }
        return ratingsToReturn;
    }
}
