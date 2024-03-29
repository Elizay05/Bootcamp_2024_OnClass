package com.example.bootcamp2024onclass.domain.api;

import com.example.bootcamp2024onclass.domain.model.Bootcamp;

public interface IBootcampServicePort {
    Bootcamp saveBootcamp(Bootcamp bootcamp);
}
