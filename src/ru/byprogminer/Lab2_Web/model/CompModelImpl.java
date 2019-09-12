package ru.byprogminer.Lab2_Web.model;

import java.math.BigDecimal;
import java.util.Arrays;

import static ru.byprogminer.Lab2_Web.utility.Utility.divide;

public class CompModelImpl implements CompModel {

    public static final int[] ALLOWED_XES = new int[] {-4, -3, -2, -1, 0, 1, 2, 3, 4};
    public static final double[] ALLOWED_YS_RANGE = new double[] {-3, 3};
    public static final int[] ALLOWED_RS = new int[] {1, 2, 3, 4, 5};

    private final BigDecimal x;
    private final BigDecimal y;
    private final BigDecimal r;

    public CompModelImpl(BigDecimal x, BigDecimal y, BigDecimal r) {
        if (x != null && Arrays.stream(ALLOWED_XES).anyMatch(value -> BigDecimal.valueOf(value).equals(x))) {
            this.x = x;
        } else {
            this.x = null;
        }

        if (y != null && y.compareTo(BigDecimal.valueOf(ALLOWED_YS_RANGE[0])) > 0 &&
                y.compareTo(BigDecimal.valueOf(ALLOWED_YS_RANGE[1])) < 0) {
            this.y = y;
        } else {
            this.y = null;
        }

        if (r != null && Arrays.stream(ALLOWED_RS).anyMatch(value -> BigDecimal.valueOf(value).equals(r))) {
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
    public BigDecimal getX() {
        return x;
    }

    @Override
    public BigDecimal getY() {
        return y;
    }

    @Override
    public BigDecimal getR() {
        return r;
    }

    @Override
    public boolean getResult(BigDecimal x, BigDecimal y, BigDecimal r) {
        BigDecimal halfR = divide(r, BigDecimal.valueOf(2));

        return (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) >= 0 &&
                x.multiply(x).add(y.multiply(y)).compareTo(halfR.multiply(halfR)) < 0) ||
               (x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) <= 0 &&
                       y.compareTo(x.subtract(halfR)) > 0) ||
               (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) >= 0 &&
                       x.compareTo(BigDecimal.ZERO.subtract(r)) >= 0 && y.compareTo(r) <= 0);
    }
}
