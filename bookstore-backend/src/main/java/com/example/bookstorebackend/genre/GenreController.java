package com.example.bookstorebackend.genre;

import com.example.bookstorebackend.genre.dto.GenreDTO;
import com.example.bookstorebackend.utils.ObjectsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bookstore/genre")
@RequiredArgsConstructor
public class GenreController {
    private final GenreRepository genreRepository;

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAll(){
        List<Genre> genres = genreRepository.findAll();
        return new ResponseEntity<>(ObjectsMapper.convertGenresToDTOs(genres), HttpStatus.OK);
    }
}
