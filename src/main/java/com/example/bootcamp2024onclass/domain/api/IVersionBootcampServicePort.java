package com.example.bootcamp2024onclass.domain.api;

import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;

import java.util.List;

public interface IVersionBootcampServicePort {
    VersionBootcamp saveVersionBootcamp(VersionBootcamp versionBootcamp);
    public List<VersionBootcamp> getAllVersionBootcamps(Integer page, Integer size, String orderBy, boolean isAscending, String bootcampName);
}
