package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.VersionBootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.DateVersionBootcampAlreadyUseException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.IVersionBootcampEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.IVersionBootcampRepository;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import com.example.bootcamp2024onclass.domain.spi.IVersionBootcampPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class VersionBootcampAdapter implements IVersionBootcampPersistencePort {

    private final IVersionBootcampRepository versionBootcampRepository;
    private final IVersionBootcampEntityMapper versionBootcampEntityMapper;
    private final IBootcampRepository bootcampRepository;

    @Override
    public VersionBootcamp saveVersionBootcamp(VersionBootcamp versionBootcamp) {
        List<VersionBootcampEntity> versions = versionBootcampRepository.findByBootcampId(versionBootcamp.getBootcampId());

        for (VersionBootcampEntity existingVersion : versions) {
            if (versionBootcamp.getStartDate().equals(existingVersion.getStartDate()) ||
                    versionBootcamp.getEndDate().equals(existingVersion.getEndDate())) {
                throw new DateVersionBootcampAlreadyUseException();
            }
        }

        BootcampEntity bootcampEntity = bootcampRepository.findById(versionBootcamp.getBootcampId())
                .orElseThrow(ElementNotFoundException::new);

        String bootcampName = bootcampEntity.getName();

        versionBootcamp.setBootcampName(bootcampName);

        VersionBootcampEntity versionBootcampEntity = versionBootcampEntityMapper.toEntity(versionBootcamp);
        versionBootcampEntity.setBootcamp(bootcampEntity);
        VersionBootcampEntity savedVersionBootcampEntity = versionBootcampRepository.save(versionBootcampEntity);

        return versionBootcampEntityMapper.toModel(savedVersionBootcampEntity);
    }
}
