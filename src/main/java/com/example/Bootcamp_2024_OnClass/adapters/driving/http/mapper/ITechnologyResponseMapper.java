package com.example.Bootcamp_2024_OnClass.adapters.driving.http.mapper;

import com.example.Bootcamp_2024_OnClass.adapters.driving.http.dto.reponse.TechnologyResponse;
import com.example.Bootcamp_2024_OnClass.domain.model.Technology;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITechnologyResponseMapper {
    TechnologyResponse toTechnologyResponse(Technology technology);
    List<TechnologyResponse> toTechnologyResponseList(List<Technology> technologies);
}
