package com.example.bootcamp2024onclass.domain.spi;

import com.example.bootcamp2024onclass.domain.model.Bootcamp;

public interface IBootcampPersistencePort {

    Bootcamp saveBootcamp(Bootcamp bootcamp);
}
