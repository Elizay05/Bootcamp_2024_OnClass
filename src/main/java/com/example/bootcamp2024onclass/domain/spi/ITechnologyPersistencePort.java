package com.example.bootcamp2024onclass.domain.spi;

import com.example.bootcamp2024onclass.domain.model.Technology;

public interface ITechnologyPersistencePort {
    void saveTechnology(Technology technology);
}
