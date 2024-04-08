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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


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

    @Override
    public List<VersionBootcamp> getAllVersionBootcamps(Integer page, Integer size, String isOrderBy, boolean isAscending, String bootcampName) {
        Sort.Direction direction = isAscending ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable;
        if (bootcampName != null && !bootcampName.isEmpty()) {
            Sort sort;
            if(isOrderBy == null || isOrderBy == "") {
                sort = Sort.by(direction, "startDate");
            } else {
                switch (isOrderBy) {
                    case "startDate":
                        sort = Sort.by(direction, "startDate");
                        break;
                    case "maximumQuota":
                        sort = Sort.by(direction, "maximumQuota");
                        break;
                    default:
                        sort = Sort.by(direction, "startDate");
                        break;
                }
            }
            pageable = PageRequest.of(page, size, sort);
        } else {
            Sort sort;
            if(isOrderBy == null || isOrderBy == "") {
                sort = Sort.by(direction, "bootcamp.name");
            } else {
                switch (isOrderBy) {
                    case "name":
                        sort = Sort.by(direction, "bootcamp.name");
                        break;
                    case "startDate":
                        sort = Sort.by(direction, "startDate");
                        break;
                    case "maximumQuota":
                        sort = Sort.by(direction, "maximumQuota");
                        break;
                    default:
                        sort = Sort.by(direction, "bootcamp.name");
                        break;
                }
            }
            pageable = PageRequest.of(page, size, sort);
        }

        List<VersionBootcampEntity> versionBootcamps;
        if (bootcampName != null && !bootcampName.isEmpty()) {
            Optional<BootcampEntity> searchBootcampOptional = bootcampRepository.findByName(bootcampName);
            if (searchBootcampOptional.isPresent()) {
                BootcampEntity bootcamp = searchBootcampOptional.get();
                versionBootcamps = versionBootcampRepository.findByBootcamp(bootcamp, pageable);
            } else {
                versionBootcamps = Collections.emptyList();
            }
        } else {
            versionBootcamps = versionBootcampRepository.findAll(pageable).getContent();
        }

        return versionBootcamps.stream()
                .map(versionBootcampEntityMapper::toModel)
                .toList();
    }
}
