package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

    public interface ICapacityRepository extends JpaRepository<CapacityEntity, Long> {
        Optional<CapacityEntity> findByName(String name);

    }
