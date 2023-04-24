package com.example.bookstorebackend.address;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    public void save(Address address) {
        addressRepository.save(address);
    }


    public Long generateId() {
        Long i = 0l;
        for (Address address:addressRepository.findAll()) {
            if(address.getId() >= i)
                i = address.getId() + 1;
        }
        return i;
    }
}
