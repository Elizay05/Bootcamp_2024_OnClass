package com.example.bootcamp2024onclass.domain.model;

import com.example.bootcamp2024onclass.domain.exception.BootcampCapacitiesRepeatException;
import com.example.bootcamp2024onclass.domain.exception.MaxSizeCapacitiesException;
import com.example.bootcamp2024onclass.domain.exception.MinSizeCapacitesException;
import com.example.bootcamp2024onclass.domain.util.DomainConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class Bootcamp {
    private final Long id;
    private final String name;
    private final String description;
    private final List<Capacity> capacities;

    public Bootcamp(Long id, String name, String description, List<Capacity> capacities) {
        this.id = id;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        this.capacities = new ArrayList<>(capacities);
        validateCapacities();
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

    public List<Capacity> getCapacities() {
        return new ArrayList<>(capacities);
    }

    private void validateCapacities() {
        if (capacities.size() < DomainConstants.MIN_SIZE_CAPACITIES) {
            throw new MinSizeCapacitesException();
        }
        Set<Long> uniqueCapacityIds = new HashSet<>();
        for (Capacity capacity : capacities) {
            if (!uniqueCapacityIds.add(capacity.getId())) {
                throw new BootcampCapacitiesRepeatException();
            }
        }
        if (capacities.size() > DomainConstants.MAX_SIZE_CAPACITIES) {
            throw new MaxSizeCapacitiesException();
        }
    }
}
