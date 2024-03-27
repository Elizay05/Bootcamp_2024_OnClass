package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityResponse;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICapacityResponseMapper {
    @Mapping(source = "technologies", target = "capacityTechnologyResponses")
    CapacityResponse toCapacityResponse(Capacity capacity);
}
