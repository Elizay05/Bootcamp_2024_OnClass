package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITechnologyRequestMapper {
    @Mapping(target = "id", ignore = true)
    Technology addRequestToTechnology(AddTechnologyRequest addTechnologyRequest);
}
