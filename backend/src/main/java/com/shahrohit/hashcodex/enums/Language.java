package com.shahrohit.hashcodex.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Language {
    CPP, JAVA, PYTHON;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static Language fromJson(String value) {
        return Language.valueOf(value.toUpperCase());
    }
}
