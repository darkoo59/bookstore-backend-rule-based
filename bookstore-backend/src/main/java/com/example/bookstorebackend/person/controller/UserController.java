package com.example.bookstorebackend.person.controller;

import com.example.bookstorebackend.exceptions.EmailExistsException;
import com.example.bookstorebackend.person.dto.RegisterDTO;
import com.example.bookstorebackend.person.service.PersonService;
import com.example.bookstorebackend.person.service.UserService;
import com.example.bookstorebackend.security.filter.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/bookstore/user")
public class UserController {
    private final UserService userService;
    private final AuthUtility authUtility;
    @Value("${frontend.base.url}")
    private String frontendBaseUrl;

    @Autowired
    public UserController(UserService userService, PersonService personService) {
        this.userService = userService;
        this.authUtility = new AuthUtility(personService);
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody RegisterDTO registerDTO) {
        try {
            userService.registerUser(registerDTO);
            return new ResponseEntity<>(OK);
        } catch (EmailExistsException e) {
            return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Unknown error", BAD_REQUEST);
        }
    }
}
