package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBootcampRepository extends JpaRepository<BootcampEntity, Long> {
    Optional<BootcampEntity> findByName(String name);

}
