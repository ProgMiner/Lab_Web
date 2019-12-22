package ru.byprogminer.Lab4_Web.api.v1.history;

import java.math.BigDecimal;

public class QueryDto {

    public final BigDecimal x, y, r;
    public final boolean result;

    public QueryDto(BigDecimal x, BigDecimal y, BigDecimal r, boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }
}
