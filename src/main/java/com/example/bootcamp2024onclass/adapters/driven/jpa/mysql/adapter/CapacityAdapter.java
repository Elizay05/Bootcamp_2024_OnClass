package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
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
        // Obtener la capacidad como una entidad
        CapacityEntity capacityEntity = capacityEntityMapper.toEntity(capacity);

        // Buscar cada tecnología en la base de datos para obtener su nombre
        for (TechnologyEntity technologyEntity : capacityEntity.getTechnologies()) {
            Optional<TechnologyEntity> technologyEntityOptional = technologyRepository.findById(technologyEntity.getId());
            if (technologyEntityOptional.isPresent()) {
                // Si se encuentra la tecnología, actualizar el nombre en la entidad de capacidad
                technologyEntity.setName(technologyEntityOptional.get().getName());
            } else {
                throw new RuntimeException("Technology not found for ID: " + technologyEntity.getId());
            }
        }

        // Guardar la capacidad actualizada en el repositorio
        capacityEntity = capacityRepository.save(capacityEntity);

        // Convertir la capacidad de vuelta a un objeto de dominio y retornarla
        return capacityEntityMapper.toDomain(capacityEntity);
    }
    /*
    @Override
    public Capacity saveCapacity(Capacity capacity) {
        CapacityEntity capacityEntity = capacityEntityMapper.toEntity(capacity);
        capacityRepository.save(capacityEntity);
        return capacity;
    }
    */
}