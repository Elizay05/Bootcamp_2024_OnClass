package com.example.bootcamp2024onclass.domain.util;

public class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' cannot be null";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field 'description' cannot be null";
    public static final Integer MIN_SIZE_TECHNOLOGIES = 3;
    public static final Integer MAX_SIZE_TECHNOLOGIES = 20;
    public static final Integer MIN_SIZE_CAPACITIES = 1;
    public static final Integer MAX_SIZE_CAPACITIES = 4;

}
