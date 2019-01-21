package ru.vmaltsev.minirtb.pagination;

import lombok.Getter;

import java.util.List;

@Getter
public class Page<T> {

    private List<T> elems;

    private Long totalPages;

    private Long totalSize;

    private Long currentPage;

    private Long currentSize;

    public Page(List<T> elems, Long totalPages, Long totalSize, Long currentPage, Long currentSize) {
        this.elems = elems;
        this.totalPages = (long) Math.ceil((double) totalSize / (double) totalPages);
        this.totalSize = totalSize;
        this.currentPage = currentPage;
        this.currentSize = currentSize;
    }
}
