package ru.vmaltsev.minirtb.range;

import lombok.Getter;

@Getter
public class RangeParams {

    private static final Double DEFAULT_RANGE_PARAM = 0.0;

    private Double x1;

    private Double y1;

    private Double x2;

    private Double y2;

    public RangeParams (Double x1, Double y1, Double x2, Double y2) {

        this.x1 = x1 == null ? DEFAULT_RANGE_PARAM : x1;

        this.x2 = x2 == null ? DEFAULT_RANGE_PARAM : x2;

        this.y1 = y1 == null ? DEFAULT_RANGE_PARAM : y1;

        this.y2 = y2 == null ? DEFAULT_RANGE_PARAM : y2;

    }
}
