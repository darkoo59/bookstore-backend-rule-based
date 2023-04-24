package com.example.bookstorebackend.person.model;

import com.example.bookstorebackend.address.Address;
import com.example.bookstorebackend.role.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.bookstorebackend.utils.enums.Sex;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
public abstract class Person {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    protected Long id;
    protected String firstname;
    protected String lastname;
    protected String email;
    protected String password;
    @ManyToMany(fetch = FetchType.EAGER)
    protected Collection<Role> roles = new ArrayList<>();
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected Address address;
    protected String phone;
    protected String nationalId;
    protected Sex sex;
    protected String occupation;
    protected String information;
}
