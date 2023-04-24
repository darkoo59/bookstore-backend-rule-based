package com.example.bookstorebackend.user.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user_")
@NoArgsConstructor
@Setter
@Getter
public class User extends Person{
}
