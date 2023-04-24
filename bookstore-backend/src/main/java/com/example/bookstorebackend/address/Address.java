package com.example.bookstorebackend.address;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;

    private String street;
    private String number;
    private String city;
    private String country;

    public Address(String street, String number, String city, String country) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
    }
}
