package com.planchella.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EventSize {
    SMALL("small") ,
    MID("medium") ,
    LARGE("large");

    private final String value;

    EventSize(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
}
