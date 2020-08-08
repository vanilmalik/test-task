package com.agileengine.testtask.dto;

public class PageableDto {

    private int page = 1;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public PageableDto(int page) {
        this.page = page;
    }
}
