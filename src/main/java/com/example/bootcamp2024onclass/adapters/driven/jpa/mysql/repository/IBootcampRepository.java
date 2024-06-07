package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IBootcampRepository extends JpaRepository<BootcampEntity, Long> {

    @Query("SELECT c FROM BootcampEntity c ORDER BY c.capacities.size ASC")
    Page<BootcampEntity> findAllOrderedByCapacitiesCountAsc(Pageable pageable);

    @Query("SELECT c FROM BootcampEntity c ORDER BY c.capacities.size DESC")
    Page<BootcampEntity> findAllOrderedByCapacitiesCountDesc(Pageable pageable);

    Page<BootcampEntity> findAll(Pageable pageable);

    Optional<BootcampEntity> findByName(String name);

}
