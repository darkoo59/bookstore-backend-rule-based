package com.example.bookstorebackend.role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;


import static jakarta.persistence.GenerationType.AUTO;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;

    public Role(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Role(String name)
    {
        this.name = name;
    }
}
