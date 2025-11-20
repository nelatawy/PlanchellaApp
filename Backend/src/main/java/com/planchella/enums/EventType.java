package com.planchella.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EventType {
    HACKATHON("hackathon"),
    RELEASE("release"),
    CONTEST("contest");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
}
