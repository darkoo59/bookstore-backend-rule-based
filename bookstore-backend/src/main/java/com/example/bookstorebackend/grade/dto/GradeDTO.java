package com.example.bookstorebackend.grade.dto;

import com.example.bookstorebackend.person.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {
    private Long id;
    int value;
    private UserDTO user;
}
