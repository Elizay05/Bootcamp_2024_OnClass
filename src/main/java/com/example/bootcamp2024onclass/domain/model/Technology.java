package com.example.bootcamp2024onclass.domain.model;

import com.example.bootcamp2024onclass.domain.util.DomainConstants;

import static java.util.Objects.requireNonNull;

public class Technology {
    public Technology() {
    }

    private Long id;

    private String name;

    private String description;

    public Technology(Long id, String name, String description){
        this.id = id;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
    }

    public Technology(Long id) {
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
