package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.TechnologyAlreadyExistsException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.bootcamp2024onclass.domain.model.Technology;
import com.example.bootcamp2024onclass.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class TechnologyAdapter implements ITechnologyPersistencePort {
    private final ITechnologyEntityMapper technologyEntityMapper;
    private final ITechnologyRepository technologyRepository;

    @Override
    public Technology saveTechnology(Technology technology){
        if(technologyRepository.findByName(technology.getName()).isPresent()){
            throw new TechnologyAlreadyExistsException();
        }
        TechnologyEntity technologyEntity = technologyEntityMapper.toEntity(technology);
        technologyEntity = technologyRepository.save(technologyEntity);
        return technologyEntityMapper.toModel(technologyEntity);
    }

}
