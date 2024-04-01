package com.example.bootcamp2024onclass.domain.spi;

import com.example.bootcamp2024onclass.domain.model.Technology;
import java.util.List;

public interface ITechnologyPersistencePort {
    Technology saveTechnology(Technology technology);
    List<Technology> getAllTechnologies(Integer page, Integer size, boolean isAscending);
}
