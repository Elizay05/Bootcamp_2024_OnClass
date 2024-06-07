package com.example.bootcamp2024onclass.domain.api.usecase;

import com.example.bootcamp2024onclass.domain.api.ITechnologyServicePort;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.model.Technology;
import com.example.bootcamp2024onclass.domain.spi.ITechnologyPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyUseCase implements ITechnologyServicePort {

    private final ITechnologyPersistencePort technologyPersistencePort;

    public TechnologyUseCase(ITechnologyPersistencePort technologyPersistencePort){
        this.technologyPersistencePort = technologyPersistencePort;
    }
    @Override
    public Technology saveTechnology(Technology technology) {
        return technologyPersistencePort.saveTechnology(technology);
    }


    @Override
    public CustomPage<Technology> getAllTechnologies(PaginationCriteria criteria) {
        return technologyPersistencePort.getAllTechnologies(criteria);
    }

    @Override
    public List<Technology> getTotalBodyTechnologies() {
        return technologyPersistencePort.getTotalBodyTechnologies();
    }

}
