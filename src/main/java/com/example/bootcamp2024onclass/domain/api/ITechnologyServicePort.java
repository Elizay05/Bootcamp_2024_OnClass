package com.example.bootcamp2024onclass.domain.api;

import com.example.bootcamp2024onclass.domain.model.Technology;

import java.util.List;

public interface ITechnologyServicePort {

    Technology saveTechnology(Technology technology);

    List<Technology> getAllTechnologies(Integer page, Integer size, Boolean isAscending);
}
