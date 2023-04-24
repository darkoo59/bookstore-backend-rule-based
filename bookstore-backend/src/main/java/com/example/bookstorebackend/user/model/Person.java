package com.example.bookstorebackend.user.model;

import com.example.bookstorebackend.address.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.bookstorebackend.utils.enums.Sex;

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
    @OneToOne(fetch = FetchType.EAGER)
    protected Address address;
    protected String phone;
    protected String nationalId;
    protected Sex sex;
    protected String occupation;
    protected String information;
}
