package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private Integer maximumQuota;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "id_bootcamp")
    private BootcampEntity bootcamp;
}
