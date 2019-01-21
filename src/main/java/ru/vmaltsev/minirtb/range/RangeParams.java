package ru.vmaltsev.minirtb.range;

import lombok.Getter;

@Getter
public class RangeParams {

    private static final Double DEFAULT_RANGE_PARAM = 0.0;

    private Double x1;

    private Double y1;

    private Double x2;

    private Double y2;

    public RangeParams(Double x1, Double y1, Double x2, Double y2) {
        if (x1 == null || y1 == null || x2 == null || y2 == null) {
            this.x1 = DEFAULT_RANGE_PARAM;

            this.x2 = DEFAULT_RANGE_PARAM;

            this.y1 = DEFAULT_RANGE_PARAM;

            this.y2 = DEFAULT_RANGE_PARAM;
        } else {
            if ((x1 <= x2) && (y1 <= y2)) {
                this.x1 = x1;

                this.x2 = x2;

                this.y1 = y1;

                this.y2 = y2;
            }
            if ((x1 >= x2) && (y1 <= y2)) {
                this.x1 = x2;

                this.x2 = x1;

                this.y1 = y1;

                this.y2 = y2;
            }
            if ((x1 <= x2) && (y1 >= y2)) {
                this.x1 = x1;

                this.x2 = x2;

                this.y1 = y2;

                this.y2 = y1;
            }
            if ((x1 >= x2) && (y1 >= y2)) {
                this.x1 = x2;

                this.x2 = x1;

                this.y1 = y2;

                this.y2 = y1;
            }
        }

    }
}
