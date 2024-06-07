package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.CapacityAlreadyExistsException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.spi.ICapacityPersistencePort;
import com.example.bootcamp2024onclass.domain.util.SortDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CapacityAdapter implements ICapacityPersistencePort {
    private final ICapacityRepository capacityRepository;
    private final ICapacityEntityMapper capacityEntityMapper;
    private final ITechnologyRepository technologyRepository;

    @Override
    public Capacity saveCapacity(Capacity capacity) {
        CapacityEntity capacityEntity = capacityEntityMapper.toEntity(capacity);
        if (capacityRepository.findByName(capacity.getName()).isPresent()) {
            throw new CapacityAlreadyExistsException();
        }
        for (TechnologyEntity technologyEntity : capacityEntity.getTechnologies()) {
            Optional<TechnologyEntity> technologyEntityOptional = technologyRepository.findById(technologyEntity.getId());
            if (technologyEntityOptional.isPresent()) {
                technologyEntity.setName(technologyEntityOptional.get().getName());
            } else {
                throw new ElementNotFoundException();
            }
        }
        capacityEntity = capacityRepository.save(capacityEntity);
        return capacityEntityMapper.toModel(capacityEntity);
    }

    @Override
    public CustomPage<Capacity> getAllCapacities(PaginationCriteria criteria) {
        Sort.Direction direction = criteria.getSortDirection() == SortDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<CapacityEntity> page;
        Pageable pageable;

        if ("name".equals(criteria.getSortBy())) {
            pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, "name"));
            page = capacityRepository.findAll(pageable);
        } else {
            pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, "technologies.size"));
            if (criteria.getSortDirection() == SortDirection.ASC) {
                page = capacityRepository.findAllOrderedByTechnologiesCountAsc(pageable);
            } else {
                page = capacityRepository.findAllOrderedByTechnologiesCountDesc(pageable);
            }
        }

        List<Capacity> capacities = capacityEntityMapper.toModelList(page.getContent());

        return new CustomPage<>(capacities, pageable.getPageNumber(), pageable.getPageSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public List<Capacity> getTotalBodyCapacities() {
        List<CapacityEntity> capacities = capacityRepository.findAll();
        if (capacities.isEmpty()) {
            throw new NoDataFoundException();
        }
        return capacityEntityMapper.toModelList(capacities);
    }
}