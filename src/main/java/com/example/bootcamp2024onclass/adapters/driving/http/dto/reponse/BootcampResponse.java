package com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class BootcampResponse {

    private final Long id;

    private final String name;

    private final String description;

    private final List<BootcampCapacityResponse> capacities;
}
