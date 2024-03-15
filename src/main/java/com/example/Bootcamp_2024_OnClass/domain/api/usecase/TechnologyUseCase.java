package com.example.Bootcamp_2024_OnClass.domain.api.usecase;

import com.example.Bootcamp_2024_OnClass.domain.api.ITechnologyServicePort;
import com.example.Bootcamp_2024_OnClass.domain.model.Technology;
import com.example.Bootcamp_2024_OnClass.domain.spi.ITechnologyPersistencePort;

import java.util.List;

public class TechnologyUseCase implements ITechnologyServicePort {

    private final ITechnologyPersistencePort technologyPersistencePort;

    public TechnologyUseCase(ITechnologyPersistencePort technologyPersistencePort){
        this.technologyPersistencePort = technologyPersistencePort;
    }
    @Override
    public void saveTechnology(Technology technology) {
        technologyPersistencePort.saveTechnology(technology);
    }

    @Override
    public List<Technology> getAllTechnologies(Integer page, Integer size, Boolean isAscending) {
        return technologyPersistencePort.getAllTechnologies(page, size, isAscending);
    }

}
