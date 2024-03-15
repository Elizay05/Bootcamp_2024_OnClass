package com.example.Bootcamp_2024_OnClass.domain.spi;

import com.example.Bootcamp_2024_OnClass.domain.model.Technology;

import java.util.List;

public interface ITechnologyPersistencePort {
    void saveTechnology(Technology technology);
    List<Technology> getAllTechnologies(Integer page, Integer size, boolean isAscending);
}
