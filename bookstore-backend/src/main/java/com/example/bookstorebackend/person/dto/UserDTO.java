package com.example.bookstorebackend.person.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    protected Long id;
    protected String firstname;
    protected String lastname;
}
