package com.example.bootcamp2024onclass.domain.model;

import com.example.bootcamp2024onclass.domain.exception.CapacityTechnologiesRepeatException;
import com.example.bootcamp2024onclass.domain.exception.MaxSizeTechnologiesException;
import com.example.bootcamp2024onclass.domain.exception.MinSizeTechnologiesException;
import com.example.bootcamp2024onclass.domain.util.DomainConstants;

import java.util.*;

import static java.util.Objects.requireNonNull;

public class Capacity {
    public Capacity() {
    }
    private Long id;
    private String name;
    private String description;
    private List<Technology> technologies;

    public Capacity(Long id, String name, String description, List<Technology> technologies) {
        this.id = id;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        this.technologies = new ArrayList<>(technologies);
        validateTechnologies();
    }
    public Capacity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Technology> getTechnologies() {
        if (technologies == null) {
            return Collections.emptyList(); // O cualquier otra acción que consideres apropiada
        }
        return new ArrayList<>(technologies);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    private void validateTechnologies() {
        if (technologies.size() < DomainConstants.MIN_SIZE_TECHNOLOGIES) {
            throw new MinSizeTechnologiesException();
        }
        Set<Long> uniqueTechnologyIds = new HashSet<>();
        for (Technology technology : technologies) {
            if (!uniqueTechnologyIds.add(technology.getId())) {
                throw new CapacityTechnologiesRepeatException();
            }
        }
        if (technologies.size() > DomainConstants.MAX_SIZE_TECHNOLOGIES) {
            throw new MaxSizeTechnologiesException();
        }
    }

}
