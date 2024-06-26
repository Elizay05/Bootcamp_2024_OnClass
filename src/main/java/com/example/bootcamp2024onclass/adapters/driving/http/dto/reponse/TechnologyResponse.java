package com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TechnologyResponse {
    private final Long id;

    private final String name;

    private final String description;
}
