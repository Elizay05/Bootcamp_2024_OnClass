package com.example.Bootcamp_2024_OnClass.adapters.driving.http.mapper;

import com.example.Bootcamp_2024_OnClass.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.example.Bootcamp_2024_OnClass.adapters.driving.http.dto.request.UpdateTechnologyRequest;
import com.example.Bootcamp_2024_OnClass.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITechnologyRequestMapper {
    @Mapping(target = "id", ignore = true)
    Technology addRequestToTechnology(AddTechnologyRequest addTechnologyRequest);

    Technology updateRequestToTechnology(UpdateTechnologyRequest updateTechnologyRequest);
}
