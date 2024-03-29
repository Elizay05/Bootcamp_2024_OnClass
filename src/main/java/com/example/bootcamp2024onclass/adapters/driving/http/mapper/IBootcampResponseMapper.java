package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampResponse;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampResponseMapper {
    @Mapping(source = "capacities", target = "capacities")
    BootcampResponse toBootcampResponse(Bootcamp bootcamp);

    List<BootcampResponse> toBootcampResponseList(List<Bootcamp> bootcamps);
}
