package com.planchella.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUser {

    @Setter(AccessLevel.NONE)
    private Long id;

    private String username;

    private String password;

}
