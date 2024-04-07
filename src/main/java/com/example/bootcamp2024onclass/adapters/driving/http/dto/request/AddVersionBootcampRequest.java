package com.example.bootcamp2024onclass.adapters.driving.http.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class AddVersionBootcampRequest {
    @NotNull(message = "bootcampId field cannot be null")
    private final Long bootcampId;

    @NotNull(message = "maximumQuota field cannot be null")
    private final Integer maximumQuota;

    @NotNull(message = "startDate field cannot be null")
    @JsonFormat(pattern="dd-MM-yyyy")
    private final LocalDate startDate;

    @NotNull(message = "endDate field cannot be null")
    @JsonFormat(pattern="dd-MM-yyyy")
    private final LocalDate endDate;
}
