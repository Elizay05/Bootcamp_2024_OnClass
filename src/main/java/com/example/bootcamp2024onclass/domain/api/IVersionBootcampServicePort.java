package com.example.bootcamp2024onclass.domain.api;

import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;

import java.util.List;

public interface IVersionBootcampServicePort {
    VersionBootcamp saveVersionBootcamp(VersionBootcamp versionBootcamp);

    CustomPage<VersionBootcamp> getAllVersionBootcamps(PaginationCriteria criteria, String bootcampName);
}
