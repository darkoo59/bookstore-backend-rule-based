package com.example.bookstorebackend.person.service;

import com.example.bookstorebackend.address.AddressService;
import com.example.bookstorebackend.person.model.Person;
import com.example.bookstorebackend.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements UserDetailsService {
    private final PersonRepository personRepository;
    private final UserService userService;
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;

    @Autowired
    public PersonService(PersonRepository personRepository, UserService userService,
                         AdminService adminService,
                         PasswordEncoder passwordEncoder, AddressService addressService) {
        this.personRepository = personRepository;
        this.userService = userService;
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
        this.addressService = addressService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public Person getPerson(String email) {
        Person person = userService.getUser(email);
        if (person == null) {
            person = adminService.getAdmin(email);
        }
        return person;
    }
}
