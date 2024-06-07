package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.BootcampAlreadyExistsException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.IBootcampEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.spi.IBootcampPersistencePort;
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
public class BootcampAdapter implements IBootcampPersistencePort {

    private final IBootcampRepository bootcampRepository;
    private final IBootcampEntityMapper bootcampEntityMapper;
    private final ICapacityRepository capacityRepository;

    @Override
    public Bootcamp saveBootcamp(Bootcamp bootcamp) {
        BootcampEntity bootcampEntity = bootcampEntityMapper.toEntity(bootcamp);
        if (bootcampRepository.findByName(bootcamp.getName()).isPresent()) {
            throw new BootcampAlreadyExistsException("Bootcamp");
        }
        for (CapacityEntity capacityEntity : bootcampEntity.getCapacities()) {
            Optional<CapacityEntity> capacityEntityOptional = capacityRepository.findById(capacityEntity.getId());
            if (capacityEntityOptional.isPresent()) {
                capacityEntity.setName(capacityEntityOptional.get().getName());
            } else {
                throw new ElementNotFoundException();
            }
        }
        bootcampEntity = bootcampRepository.save(bootcampEntity);
        return bootcampEntityMapper.toModel(bootcampEntity);
    }

    @Override
    public CustomPage<Bootcamp> getAllBootcamps(PaginationCriteria criteria) {
        Sort.Direction direction = criteria.getSortDirection() == SortDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<BootcampEntity> page;
        Pageable pageable;

        if ("name".equals(criteria.getSortBy())) {
            pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, "name"));
            page = bootcampRepository.findAll(pageable);
        } else {
            pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, "capacities.size"));
            if (criteria.getSortDirection() == SortDirection.ASC) {
                page = bootcampRepository.findAllOrderedByCapacitiesCountAsc(pageable);
            } else {
                page = bootcampRepository.findAllOrderedByCapacitiesCountDesc(pageable);
            }
        }

        List<Bootcamp> bootcamps = bootcampEntityMapper.toModelList(page.getContent());

        return new CustomPage<>(bootcamps, pageable.getPageNumber(), pageable.getPageSize(), page.getTotalElements(), page.getTotalPages());
    }

}
