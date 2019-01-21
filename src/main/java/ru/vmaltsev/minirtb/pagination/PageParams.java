package ru.vmaltsev.minirtb.pagination;

import lombok.Getter;

@Getter
public class PageParams {
    private static final Long DEFAULT_PAGE = 1L;
    private static final Long DEFAULT_SIZE = 10L;

    private Long page;
    private Long size;

    public PageParams(Long page, Long size) {

        this.page = page == null ? DEFAULT_PAGE : page;

        this.size = size == null ? DEFAULT_SIZE : Math.min(size, 500);

    }
}
