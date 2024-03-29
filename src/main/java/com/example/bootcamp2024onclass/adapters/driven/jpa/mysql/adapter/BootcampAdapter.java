package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.BootcampAlreadyExistsException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.IBootcampEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import com.example.bootcamp2024onclass.domain.spi.IBootcampPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
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
            throw new BootcampAlreadyExistsException();
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
    public List<Bootcamp> getAllBootcamps(Integer page, Integer size, boolean isOrderByName, boolean isAscending) {
        Sort.Direction direction = isAscending ? Sort.Direction.ASC : Sort.Direction.DESC;
        String field = isOrderByName ? "name" : "id";
        Sort sort = Sort.by(direction, field);
        List<BootcampEntity> bootcamps = bootcampRepository.findAll(sort);

        if (!isOrderByName) {
            bootcamps.sort(Comparator.comparingInt(bootcamp -> bootcamp.getCapacities().size()));
            if (!isAscending) {
                Collections.reverse(bootcamps);
            }
        }

        return bootcamps.stream()
                .skip((long) page * size)
                .limit(size)
                .map(bootcampEntityMapper::toModel)
                .toList();
    }

}
