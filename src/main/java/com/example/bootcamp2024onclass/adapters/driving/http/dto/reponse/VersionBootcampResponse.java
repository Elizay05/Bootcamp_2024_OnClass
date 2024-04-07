package com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class VersionBootcampResponse {
    private final Long id;
    private final Long bootcampId;
    private final String bootcampName;
    private final Integer maximumQuota;
    private final LocalDate startDate;
    private final LocalDate endDate;
}
