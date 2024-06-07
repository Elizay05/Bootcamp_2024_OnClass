package com.example.bootcamp2024onclass.domain.model;

import com.example.bootcamp2024onclass.domain.util.SortDirection;

public class PaginationCriteria {

    private int page;
    private int size;
    private SortDirection sortDirection;
    private String sortBy;

    public PaginationCriteria(int page, int size, SortDirection sortDirection, String sortBy) {
        this.page = page;
        this.size = size;
        this.sortDirection = sortDirection;
        this.sortBy = sortBy;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
