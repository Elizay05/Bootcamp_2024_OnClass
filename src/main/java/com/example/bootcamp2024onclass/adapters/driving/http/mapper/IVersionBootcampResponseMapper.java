package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.VersionBootcampResponse;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IVersionBootcampResponseMapper {
    VersionBootcampResponse toVersionBootcampResponse(VersionBootcamp versionBootcamp);
    List<VersionBootcampResponse> toVersionBootcampResponseList(List<VersionBootcamp> versionBootcamps);
}
