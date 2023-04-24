package com.example.bookstorebackend.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    public Long id;
    public String street;
    public String number;
    public String city;
    public String country;
}
