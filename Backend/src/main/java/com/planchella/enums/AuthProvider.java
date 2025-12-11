package com.planchella.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AuthProvider {
    LOCAL("local"),
    GOOGLE("google"),
    GITHUB("github");

    private final String value;

    AuthProvider(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

}
