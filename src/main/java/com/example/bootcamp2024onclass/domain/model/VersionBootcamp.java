package com.example.bootcamp2024onclass.domain.model;

import java.time.LocalDate;

public class VersionBootcamp {
    private final Long id;
    private final Long bootcampId;
    private String bootcampName;
    private final Integer maximumQuota;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public VersionBootcamp(Long id, Long bootcampId, Integer maximumQuota, LocalDate startDate, LocalDate endDate){
        this.id = id;
        this.bootcampId = bootcampId;
        this.bootcampName = "";
        this.maximumQuota = maximumQuota;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public Long getBootcampId() {
        return bootcampId;
    }

    public String getBootcampName() {
        return bootcampName;
    }
    public Integer getMaximumQuota() {
        return maximumQuota;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setBootcampName(String bootcampName) {
        this.bootcampName = bootcampName;
    }

}
