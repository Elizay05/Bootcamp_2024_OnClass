package com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.mapper;

import com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.Bootcamp_2024_OnClass.domain.model.Technology;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITechnologyEntityMapper {
    Technology toModel(TechnologyEntity technologyEntity);

    TechnologyEntity toEntity(Technology technology);

    List<Technology> toModelList(List<TechnologyEntity> technologyEntities);

}
