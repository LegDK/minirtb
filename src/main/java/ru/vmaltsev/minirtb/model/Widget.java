package ru.vmaltsev.minirtb.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Widget {

    Long id;

    private Double x;

    private Double y;

    private Double width;

    private Double height;

    private Long zIndex;
}
