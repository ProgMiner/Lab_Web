package ru.byprogminer.Lab2_Web;

import java.math.BigDecimal;

public class HistoryNode {

    public final BigDecimal x;
    public final BigDecimal y;
    public final BigDecimal r;
    public final boolean result;

    public final HistoryNode next;

    public HistoryNode(BigDecimal x, BigDecimal y, BigDecimal r, boolean result, HistoryNode next) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.next = next;
    }
}
