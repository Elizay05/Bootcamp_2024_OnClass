package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ICapacityRepository extends JpaRepository<CapacityEntity, Long> {

    @Query("SELECT c FROM CapacityEntity c ORDER BY c.technologies.size ASC")
    Page<CapacityEntity> findAllOrderedByTechnologiesCountAsc(Pageable pageable);

    @Query("SELECT c FROM CapacityEntity c ORDER BY c.technologies.size DESC")
    Page<CapacityEntity> findAllOrderedByTechnologiesCountDesc(Pageable pageable);

    Page<CapacityEntity> findAll(Pageable pageable);

    Optional<CapacityEntity> findByName(String name);

}
