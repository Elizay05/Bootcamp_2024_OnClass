package com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.adapter;

import com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.exception.TechnologyAlreadyExistsException;
import com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.Bootcamp_2024_OnClass.domain.model.Technology;
import com.example.Bootcamp_2024_OnClass.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

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
    @Override
    public List<Technology> getAllTechnologies(Integer page, Integer size, boolean isAscending) {
        Sort sort = isAscending ? Sort.by(Sort.Direction.ASC, "name") : Sort.by(Sort.Direction.DESC, "name");
        Pageable pagination = PageRequest.of(page, size, sort);
        List<TechnologyEntity> technologies = technologyRepository.findAll(pagination).getContent();
        if (technologies.isEmpty()) {
            throw new NoDataFoundException();
        }
        return technologyEntityMapper.toModelList(technologies);
    }
}
