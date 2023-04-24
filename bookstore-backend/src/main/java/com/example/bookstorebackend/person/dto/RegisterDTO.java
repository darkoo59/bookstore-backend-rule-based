package com.example.bookstorebackend.person.dto;

import com.example.bookstorebackend.address.AddressDTO;
import com.example.bookstorebackend.utils.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    public String firstname;
    public String lastname;
    public String email;
    public String password;
    public String confirmPassword;
    public AddressDTO address;
    public String phone;
    public String nationalId;
    public Sex sex;
    public String occupation;
    public String information;
}
