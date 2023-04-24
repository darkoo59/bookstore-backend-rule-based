package com.example.bookstorebackend.user.repository;

import com.example.bookstorebackend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
