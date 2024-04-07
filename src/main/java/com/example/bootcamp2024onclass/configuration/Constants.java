package com.example.bootcamp2024onclass.configuration;

public class Constants {
    private Constants(){
        throw new IllegalStateException("Utility Class");
    }

    public static final String NO_DATA_FOUND_EXCEPTION_MESSAGE = "No data was found in the database";
    public static final String ELEMENT_NOT_FOUND_EXCEPTION_MESSAGE = "The element indicated does not exist";
    public static final String TECHNOLOGY_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The technology you want to create already exists";
    public static final String CAPACITY_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The capacity you want to create already exists";
    public static final String INVALID_MIN_TECHNOLOGIES_EXCEPTION_MESSAGE = "A capacity must have at least 3 associated technologies.";
    public static final String CAPACITY_TECHNOLOGIES_REPEAT_EXCEPTION_MESSAGE = "A capacity cannot have repeated technologies.";
    public static final String INVALID_MAX_TECHNOLOGIES_EXCEPTION_MESSAGE = "A capacity cannot have more than 20 associated technologies.";

    public static final String DATE_VERSIONBOOTCAMP_ALREADY_USE_EXCEPTION_MESSAGE = "The start or end date is already in use for this bootcamp.";
    public static final String BOOTCAMP_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The bootcamp you want to create already exists";
    public static final String INVALID_MIN_CAPACITIES_EXCEPTION_MESSAGE = "A bootcamp must have at least 1 capacity associated with it.";
    public static final String BOOTCAMP_CAPACITIES_REPEAT_EXCEPTION_MESSAGE = "A bootcamp cannot have repeated capacities.";
    public static final String INVALID_MAX_CAPACITIES_EXCEPTION_MESSAGE = "A bootcamp cannot have more than 4 associated capacities.";


    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "Field %s can not be empty";
    public static final String NEGATIVE_NOT_ALLOWED_EXCEPTION_MESSAGE = "Field %s can not receive negative values";
    public static final String SUPPLIER_NOT_FOUND_EXCEPTION_MESSAGE = "The supplier indicated for this product does not exist";

    public static final String STARTDATE_BEFORE_CURRENTDATE_EXCEPTION_MESSAGE = "The start date cannot be earlier than the current date.";
    public static final String STARTDATE_AFTER_ENDDATE_EXCEPTION_MESSAGE = "The start date cannot be later than the end date.";
}
