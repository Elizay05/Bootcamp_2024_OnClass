package com.example.bootcamp2024onclass.adapters.driving.http.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class AddVersionBootcampRequest {
    private final Long bootcampId;

    @NotNull
    private final Integer maximumQuota;

    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy") //Validar una expresión regular tipo dd/MM/yyyy///
    // /Se puede validar desde la validación que la fechaFin sea mayor que la fechaInicio
    private final LocalDate startDate;

    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private final LocalDate endDate;
}
