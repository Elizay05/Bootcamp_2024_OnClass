package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddVersionBootcampRequest;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IVersionBootcampRequestMapper {

    @Mapping(target = "id", ignore = true)
    VersionBootcamp addRequestToVersionBootcamp(AddVersionBootcampRequest addVersionBootcampRequest);
}
