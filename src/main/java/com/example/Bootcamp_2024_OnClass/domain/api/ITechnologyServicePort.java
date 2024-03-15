package com.example.Bootcamp_2024_OnClass.domain.api;

import com.example.Bootcamp_2024_OnClass.domain.model.Technology;

import java.util.List;

public interface ITechnologyServicePort {
    void saveTechnology(Technology technology);
    List<Technology> getAllTechnologies(Integer page, Integer size, Boolean isAscending);
}
