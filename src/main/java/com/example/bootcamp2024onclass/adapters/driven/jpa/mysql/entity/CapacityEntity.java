package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "capacity")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CapacityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "capacity_id")
    private Long id;

    @NotBlank(message = "Name field cannot be empty")
    @Size(max = 50, message = "Name field exceeds maximum length")
    private String name;

    @NotBlank(message = "Description field cannot be empty")
    @Size(max = 90, message = "Description field exceeds maximum length")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
            //CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "capacity_technology",
            joinColumns = @JoinColumn(name = "capacity_id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id")
    )
    private Set<TechnologyEntity> technologies =  new HashSet<>();
}
