package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "versionBootcamp")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VersionBootcampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Integer maximumQuota;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "id_bootcamp")
    private BootcampEntity bootcamp;
}
