package com.example.bootcamp2024onclass.domain.api;

import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.model.Technology;

import java.util.List;

public interface ITechnologyServicePort {
    Technology saveTechnology(Technology technology);
    CustomPage<Technology> getAllTechnologies(PaginationCriteria criteria);
    List<Technology> getTotalBodyTechnologies();
}
