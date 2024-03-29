package com.example.bootcamp2024onclass.domain.spi;

import com.example.bootcamp2024onclass.domain.model.Bootcamp;

import java.util.List;

public interface IBootcampPersistencePort {

    Bootcamp saveBootcamp(Bootcamp bootcamp);

    List<Bootcamp> getAllBootcamps(Integer page, Integer size, boolean orderByName, boolean isAscending);
}
