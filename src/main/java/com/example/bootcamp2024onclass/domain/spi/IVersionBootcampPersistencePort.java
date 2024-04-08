package com.example.bootcamp2024onclass.domain.spi;

import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;

import java.util.List;

public interface IVersionBootcampPersistencePort {

    VersionBootcamp saveVersionBootcamp(VersionBootcamp versionBootcamp);

    public List<VersionBootcamp> getAllVersionBootcamps(Integer page, Integer size, String orderBy, boolean isAscending, String bootcampName);
}
