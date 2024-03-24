package com.example.bootcamp2024onclass.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AddCapacityRequest {
    private final String name;

    private final String description;

    private final List<Long> technologyIds;
}