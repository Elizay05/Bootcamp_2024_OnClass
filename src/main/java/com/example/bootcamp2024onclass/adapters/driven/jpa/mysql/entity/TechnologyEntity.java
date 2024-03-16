package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "technology")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TechnologyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name field cannot be empty")
    @Size(max = 50, message = "Name field exceeds maximum length")
    private String name;

    @NotBlank(message = "Description field cannot be empty")
    @Size(max = 90, message = "Description field exceeds maximum length")
    private String description;
}
