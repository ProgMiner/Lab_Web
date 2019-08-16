package ru.byprogminer.Lab2_Web.model;

import java.util.Arrays;

public class CompModelImpl implements CompModel {

    public static final int[] ALLOWED_XES = new int[] {-4, -3, -2, -1, 0, 1, 2, 3, 4};
    public static final double[] ALLOWED_YS_RANGE = new double[] {-3, 3};
    public static final int[] ALLOWED_RS = new int[] {1, 2, 3, 4, 5};

    private final Integer x;
    private final Double y;
    private final Integer r;

    public CompModelImpl(Integer x, Double y, Integer r) {
        if (x != null && Arrays.stream(ALLOWED_XES).anyMatch(x::equals)) {
            this.x = x;
        } else {
            this.x = null;
        }

        if (y != null && y >= ALLOWED_YS_RANGE[0] && y <= ALLOWED_YS_RANGE[1]) {
            this.y = y;
        } else {
            this.y = null;
        }

        if (r != null && Arrays.stream(ALLOWED_RS).anyMatch(r::equals)) {
            this.r = r;
        } else {
            this.r = null;
        }
    }

    @Override
    public boolean isResultAvailable() {
        return x != null && y != null && r != null;
    }

    @Override
    public Number getX() {
        return x;
    }

    @Override
    public Number getY() {
        return y;
    }

    @Override
    public Number getR() {
        return r;
    }

    @Override
    public boolean getResult(double x, double y, double r) {
        double halfR = r / 2;

        return (x >= 0 && y >= 0 && x * x + y * y < halfR * halfR) ||
               (x >= 0 && y <= 0 && y > x - halfR) ||
               (x <= 0 && y >= 0 && x >= -r && y <= r);
    }
}
