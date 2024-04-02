package com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;


@AllArgsConstructor
@Getter
public class BootcampCapacityResponse {
    private final Long id;
    private final String name;
    private final List<CapacityTechnologyResponse> technologies;
}
