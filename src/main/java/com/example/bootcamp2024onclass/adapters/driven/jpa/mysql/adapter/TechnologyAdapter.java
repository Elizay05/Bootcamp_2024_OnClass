package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.TechnologyAlreadyExistsException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.model.Technology;
import com.example.bootcamp2024onclass.domain.spi.ITechnologyPersistencePort;
import com.example.bootcamp2024onclass.domain.util.SortDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

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

    @Override
    public CustomPage<Technology> getAllTechnologies(PaginationCriteria criteria) {
        Sort sort = Sort.by(criteria.getSortDirection() == SortDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, criteria.getSortBy());
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), sort);
        Page<TechnologyEntity> page = technologyRepository.findAll(pageable);

        if (page.isEmpty()) {
            throw new NoDataFoundException();
        }

        List<Technology> technologies = technologyEntityMapper.toModelList(page.getContent());
        return new CustomPage<>(technologies, pageable.getPageNumber(), pageable.getPageSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public List<Technology> getTotalBodyTechnologies() {
        List<TechnologyEntity> technologies = technologyRepository.findAll();
        if (technologies.isEmpty()) {
            throw new NoDataFoundException();
        }
        return technologyEntityMapper.toModelList(technologies);
    }
}
