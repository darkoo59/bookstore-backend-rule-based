package com.example.bookstorebackend.person.dto;

import com.example.bookstorebackend.genre.dto.GenreDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    protected Long id;
    protected String firstname;
    protected String lastname;
    protected String email;
    protected List<GenreDTO> genres;
}
