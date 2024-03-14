package com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.mapper;

import com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.Bootcamp_2024_OnClass.domain.model.Technology;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITechnologyEntityMapper {
    Technology toModel(TechnologyEntity technologyEntity);

    TechnologyEntity toEntity(Technology technology);

}
