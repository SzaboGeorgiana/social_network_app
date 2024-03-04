package com.example.lab6_fx_15_11.repo.paging;

public class PageableImplementation implements Pageable {

    private int pageNumber;
    private int pageSize;

    public PageableImplementation(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }
}
