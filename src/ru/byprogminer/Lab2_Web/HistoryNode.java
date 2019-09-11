package ru.byprogminer.Lab2_Web;

import java.math.BigDecimal;
import java.util.Objects;

public class HistoryNode {

    public final BigDecimal x;
    public final BigDecimal y;
    public final BigDecimal r;
    public final boolean result;

    public HistoryNode(BigDecimal x, BigDecimal y, BigDecimal r, boolean result) {
        this.x = Objects.requireNonNull(x);
        this.y = Objects.requireNonNull(y);
        this.r = Objects.requireNonNull(r);
        this.result = result;
    }
}
