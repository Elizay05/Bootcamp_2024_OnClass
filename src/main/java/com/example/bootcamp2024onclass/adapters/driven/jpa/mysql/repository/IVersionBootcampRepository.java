package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.VersionBootcampEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IVersionBootcampRepository extends JpaRepository<VersionBootcampEntity, Long> {
    List<VersionBootcampEntity> findByBootcampId(Long bootcampId);
}
