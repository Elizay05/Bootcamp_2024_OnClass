package com.example.bootcamp2024onclass.domain.spi;

import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;

public interface IBootcampPersistencePort {

    Bootcamp saveBootcamp(Bootcamp bootcamp);

    CustomPage<Bootcamp> getAllBootcamps(PaginationCriteria criteria);
}
