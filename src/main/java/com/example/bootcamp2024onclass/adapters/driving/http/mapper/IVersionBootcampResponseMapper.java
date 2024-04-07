package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.VersionBootcampResponse;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IVersionBootcampResponseMapper {
    VersionBootcampResponse toVersionBootcampResponse(VersionBootcamp versionBootcamp);
}
