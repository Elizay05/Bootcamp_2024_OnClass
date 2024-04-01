package com.example.bootcamp2024onclass.adapters.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AddTechnologyRequest {
    @NotBlank(message = "Name field cannot be empty")
    @Size(max = 50, message = "Name field exceeds maximum length")
    private String name;

    @NotBlank(message = "Description field cannot be empty")
    @Size(max = 90, message = "Description field exceeds maximum length")
    private String description;
}
