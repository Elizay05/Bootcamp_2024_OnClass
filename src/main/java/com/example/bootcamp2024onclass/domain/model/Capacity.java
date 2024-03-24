package com.example.bootcamp2024onclass.domain.model;

import java.util.*;

public class Capacity {
    private final Long id;
    private final String name;
    private final String description;
    private final List<Technology> technologies;

    public Capacity(Long id, String name, String description, List<Technology> technologies) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.technologies = new ArrayList<Technology>(technologies);
        validateUniqueTechnologies();
    }
    /*
    public Capacity(Long id, String name, String description, List<Optional<TechnologyEntity>> list) {
        this.id = id;
        this.name = name;
        this.description = description;
    }*/

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

    private void validateUniqueTechnologies() {
        Set<Technology> uniqueTechnologies = new HashSet<>(technologies);
        if (uniqueTechnologies.size() != technologies.size()) {
            throw new IllegalArgumentException("A capacity cannot have repeated technologies.");
        }
    }

}
