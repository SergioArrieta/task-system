package com.zerst.taskSystem.serviceUsers.entities;

import com.zerst.taskSystem.serviceUsers.services.UserDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;

    public UserDTO toDTO() {
        return new UserDTO(id, name, surname, email);
    }
}
