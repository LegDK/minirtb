package ru.vmaltsev.minirtb.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Page<T> {

    private List<T> elems;

    private Long totalPages;

    private Long totalSize;

    private Long currentPage;

    private Long currentSize;
}
