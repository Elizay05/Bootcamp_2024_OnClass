package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.VersionBootcampEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IVersionBootcampRepository extends JpaRepository<VersionBootcampEntity, Long> {
    List<VersionBootcampEntity> findByBootcampId(Long bootcampId);

    Page<VersionBootcampEntity> findByBootcamp(BootcampEntity bootcamp, Pageable pageable);

    Page<VersionBootcampEntity> findAll(Pageable pageable);

    @Query("SELECT c FROM VersionBootcampEntity c ORDER BY c.maximumQuota ASC")
    Page<VersionBootcampEntity> findByMaximumQuotaAsc(Pageable pageable);

    @Query("SELECT c FROM VersionBootcampEntity c ORDER BY c.maximumQuota DESC")
    Page<VersionBootcampEntity> findByMaximumQuotaDesc(Pageable pageable);
}
