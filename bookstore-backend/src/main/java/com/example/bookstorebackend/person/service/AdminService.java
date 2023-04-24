package com.example.bookstorebackend.person.service;

import com.example.bookstorebackend.person.model.Admin;
import com.example.bookstorebackend.person.model.Person;
import com.example.bookstorebackend.person.repository.AdminRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    public Admin getAdmin(String email) {
        return adminRepository.findByEmail(email);
    }
}
