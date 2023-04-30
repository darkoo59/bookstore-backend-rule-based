package com.example.bookstorebackend.utils;

import com.example.bookstorebackend.person.dto.RegisterDTO;
import com.example.bookstorebackend.person.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class ObjectsMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static User convertRegisterDTOToUser(RegisterDTO registerDTO) {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<RegisterDTO, User> answerMap = new PropertyMap<>() {
            protected void configure() {
                skip(destination.getId());
                map().setFirstname(source.getFirstname());
                map().setLastname(source.getLastname());
                map().setEmail(source.getEmail());
                map().setPassword(source.getPassword());
                map().setPhone(source.getPhone());
                map().setNationalId(source.getNationalId());
                map().setGender(source.getGender());
                map().setOccupation(source.getOccupation());
                map().setInformation(source.getInformation());
            }
        };

        modelMapper.addMappings(answerMap);
        return modelMapper.map(registerDTO, User.class);
    }
}
