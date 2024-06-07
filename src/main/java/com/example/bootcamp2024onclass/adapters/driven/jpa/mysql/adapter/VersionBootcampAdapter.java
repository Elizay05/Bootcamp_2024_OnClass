package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.VersionBootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.DateVersionBootcampAlreadyUseException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.IVersionBootcampEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.IVersionBootcampRepository;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import com.example.bootcamp2024onclass.domain.spi.IVersionBootcampPersistencePort;
import com.example.bootcamp2024onclass.domain.util.SortDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

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
    public CustomPage<VersionBootcamp> getAllVersionBootcamps(PaginationCriteria criteria, String bootcampName) {
        Sort.Direction direction = criteria.getSortDirection() == SortDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<VersionBootcampEntity> page;
        Pageable pageable;

        if ("startDate".equals(criteria.getSortBy())) {
            pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, "startDate"));
        } else {
            pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, "maximumQuota"));
        }

        if (bootcampName != null && !bootcampName.isEmpty()) {
            Optional<BootcampEntity> searchBootcampOptional = bootcampRepository.findByName(bootcampName);
            if (searchBootcampOptional.isPresent()) {
                BootcampEntity bootcamp = searchBootcampOptional.get();
                page = versionBootcampRepository.findByBootcamp(bootcamp, pageable);
            } else {
                page = new PageImpl<>(Collections.emptyList(), pageable, 0);
            }
        } else if ("startDate".equals(criteria.getSortBy())) {
            page = versionBootcampRepository.findAll(pageable);
        } else {
            if (criteria.getSortDirection() == SortDirection.ASC) {
                page = versionBootcampRepository.findByMaximumQuotaAsc(pageable);
            } else {
                page = versionBootcampRepository.findByMaximumQuotaDesc(pageable);
            }
        }

        List<VersionBootcamp> versionBootcamps = page.getContent().stream()
                .map(versionBootcampEntityMapper::toModel)
                .toList();

        return new CustomPage<>(versionBootcamps, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    public boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public Sort getSortForOrderBy(String orderBy, Sort.Direction direction) {
        switch (orderBy) {
            case "startDate":
                return Sort.by(direction, "startDate");
            case "maximumQuota":
                return Sort.by(direction, "maximumQuota");
            default:
                return Sort.by(direction, "startDate");
        }
    }
}
