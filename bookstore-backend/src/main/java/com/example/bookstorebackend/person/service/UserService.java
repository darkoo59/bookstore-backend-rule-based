package com.example.bookstorebackend.person.service;

import com.example.bookstorebackend.ConfirmationToken.ConfirmationToken;
import com.example.bookstorebackend.ConfirmationToken.ConfirmationTokenService;
import com.example.bookstorebackend.address.Address;
import com.example.bookstorebackend.address.AddressRepository;
import com.example.bookstorebackend.address.AddressService;
import com.example.bookstorebackend.exceptions.EmailExistsException;
import com.example.bookstorebackend.exceptions.MessagingException;
import com.example.bookstorebackend.person.dto.RegisterDTO;
import com.example.bookstorebackend.person.model.Person;
import com.example.bookstorebackend.person.model.User;
import com.example.bookstorebackend.person.repository.UserRepository;
import com.example.bookstorebackend.role.Role;
import com.example.bookstorebackend.role.RoleRepository;
import com.example.bookstorebackend.utils.ObjectsMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final AddressService addressService;
    public User getUser(String email) {
        return userRepository.findByEmail(email).isPresent() ?
                userRepository.findByEmail(email).get() : null;
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email).get();
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }
    public void registerUser(RegisterDTO registerDTO) throws EmailExistsException{
        User user = ObjectsMapper.convertRegisterDTOToUser(registerDTO);
        boolean emailExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (emailExists) {
            throw new EmailExistsException();
        }
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = new Role(0l, "ROLE_USER");
            roleRepository.save(role);
        }
        saveUser(user);
        addRoleToUser(user.getEmail(), role.getName());

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
    }
}
