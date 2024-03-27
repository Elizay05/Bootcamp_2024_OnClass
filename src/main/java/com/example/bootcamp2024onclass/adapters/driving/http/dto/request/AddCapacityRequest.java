package com.example.bootcamp2024onclass.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@Getter
public class AddCapacityRequest {
    @NotBlank(message = "Name field cannot be empty")
    @Size(max = 50, message = "Name field exceeds maximum length")
    private final String name;

    @NotBlank(message = "Description field cannot be empty")
    @Size(max = 90, message = "Description field exceeds maximum length")
    private final String description;

    private final List<Long> technologyIds;
}