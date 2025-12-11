package com.planchella.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import com.planchella.enums.AuthProvider;
import org.hibernate.annotations.Cascade;

@Getter
@Setter
@Entity
@Table(
        name = "auth_user",
        indexes = {
                @Index(name = "idx_username", columnList = "username")
        }
)
public class AuthUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private Long userId;

    private String username;

    private String password;

    private String email;

    private String providerId;

    private AuthProvider provider;

}
