package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.CapacityAlreadyExistsException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.spi.ICapacityPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        return capacityEntityMapper.toDomain(capacityEntity);
    }
}