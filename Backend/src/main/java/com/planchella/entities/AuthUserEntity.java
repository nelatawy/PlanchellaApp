package com.planchella.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import com.planchella.enums.AuthProvider;
import org.hibernate.annotations.Cascade;

@Getter
@Setter
@Entity
@Table(name = "auth_users", indexes = {
                @Index(name = "idx_username", columnList = "username")
})
public class AuthUserEntity {

        @Id
        private Long id;

        @Column(name = "user_id")
        private Long userId;

        @Column(name = "username")
        private String username;

        @Column(name = "password")
        private String password;

        @Column(name = "email")
        private String email;

        @Column(name = "provider_id")
        private String providerId;

        @Column(name = "provider")
        private AuthProvider provider;

}
