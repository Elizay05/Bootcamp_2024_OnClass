package com.example.bootcamp2024onclass.domain.model;

import com.example.bootcamp2024onclass.domain.util.DomainConstants;

import static java.util.Objects.requireNonNull;

public class Technology {

    private final Long id;

    private final String name;

    private final String description;

    public Technology(Long id, String name, String description){
        this.id = id;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
    }

    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getDescription() {
        return description;
    }

}
