package com.example.Bootcamp_2024_OnClass.domain.spi;

import com.example.Bootcamp_2024_OnClass.domain.model.Technology;

public interface ITechnologyPersistencePort {
    void saveTechnology(Technology technology);
}
