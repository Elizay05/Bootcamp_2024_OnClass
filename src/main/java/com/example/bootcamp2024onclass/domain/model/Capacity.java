package com.example.bootcamp2024onclass.domain.model;

import com.example.bootcamp2024onclass.domain.exception.CapacityTechnologiesRepeatException;
import com.example.bootcamp2024onclass.domain.exception.MaxSizeTechnologiesException;
import com.example.bootcamp2024onclass.domain.exception.MinSizeTechnologiesException;
import com.example.bootcamp2024onclass.domain.util.DomainConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class Capacity {
    private final Long id;
    private final String name;
    private final String description;
    private final List<Technology> technologies;

    public Capacity(Long id, String name, String description, List<Technology> technologies) {
        this.id = id;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        this.technologies = new ArrayList<>(technologies);
        validateTechnologies();
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
        return new ArrayList<>(technologies);
    }

    private void validateTechnologies() {
        if (technologies.size() < 3) {
            throw new MinSizeTechnologiesException();
        }
        Set<Long> uniqueTechnologyIds = new HashSet<>();
        for (Technology technology : technologies) {
            if (!uniqueTechnologyIds.add(technology.getId())) {
                throw new CapacityTechnologiesRepeatException();
            }
        }
        if (technologies.size() > 20) {
            throw new MaxSizeTechnologiesException();
        }
    }

}
