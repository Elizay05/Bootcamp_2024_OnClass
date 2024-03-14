package com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.adapter;

import com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.exception.TechnologyAlreadyExistsException;
import com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.Bootcamp_2024_OnClass.domain.model.Technology;
import com.example.Bootcamp_2024_OnClass.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class TechnologyAdapter implements ITechnologyPersistencePort {
    private final ITechnologyEntityMapper technologyEntityMapper;
    private final ITechnologyRepository technologyRepository;

    @Override
    public void saveTechnology(Technology technology){
        if(technologyRepository.findByName(technology.getName()).isPresent()){
            throw new TechnologyAlreadyExistsException();
        }
        technologyRepository.save(technologyEntityMapper.toEntity(technology));
    }

}
