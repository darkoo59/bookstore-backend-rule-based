package com.example.bookstorebackend.person.controller;

import com.example.bookstorebackend.exceptions.EmailExistsException;
import com.example.bookstorebackend.person.dto.RegisterDTO;
import com.example.bookstorebackend.person.dto.UserDTO;
import com.example.bookstorebackend.person.model.User;
import com.example.bookstorebackend.person.service.PersonService;
import com.example.bookstorebackend.person.service.UserService;
import com.example.bookstorebackend.security.filter.AuthUtility;
import com.example.bookstorebackend.utils.ObjectsMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

            return new ResponseEntity<>("Unknown error", BAD_REQUEST);
        }
    }

    @PostMapping("/token/refresh")
    //@Secured("ROLE_USER")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String accessToken = authUtility.createJWTFromRequest(request);
            if (accessToken != null) {
                response.setContentType(APPLICATION_JSON_VALUE);
                AuthUtility.setResponseMessage(response, "accessToken", accessToken);
            }
            else {
                response.setStatus(UNAUTHORIZED.value());
                AuthUtility.setResponseMessage(response, "errorMessage", "Refresh token is missing");
            }
        } catch (Exception e) {
            response.setStatus(UNAUTHORIZED.value());
            AuthUtility.setResponseMessage(response, "errorMessage", e.getMessage());
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        Cookie jwtCookie = new Cookie("refreshToken", "");
        jwtCookie.setMaxAge(0);
        jwtCookie.setDomain("localhost");
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        AuthUtility.setResponseMessage(response, "Success", "Cookie removed");
    }

    @GetMapping
    public ResponseEntity<UserDTO> get(HttpServletRequest request) {
        String email = AuthUtility.getEmailFromRequest(request);
        try{
            User user = userService.getUser(email);
            return new ResponseEntity<>(ObjectsMapper.convertUserToDTO(user), OK);
        }catch (Exception e){
            return new ResponseEntity<>(BAD_REQUEST);
        }
    }

    @PutMapping("/genre")
    public void updateFavouriteGenres(HttpServletRequest request, HttpServletResponse response, @RequestBody List<Long> genreIds){
        String email = AuthUtility.getEmailFromRequest(request);
        try{
            userService.updateGenres(email, genreIds);
        }catch (Exception e){
            response.setStatus(BAD_REQUEST.value());
        }
    }
}
