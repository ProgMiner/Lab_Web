package ru.byprogminer.Lab4_Web.history;

import java.io.Serializable;
import java.math.BigDecimal;

public class QueryEntity implements Serializable {

    private final Long id;
    private final long userId;

    private final BigDecimal x, y, r;
    private final boolean result;

    public QueryEntity(Long id, long userId, BigDecimal x, BigDecimal y, BigDecimal r, boolean result) {
        this.id = id;
        this.userId = userId;
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public BigDecimal getR() {
        return r;
    }

    public boolean getResult() {
        return result;
    }
}
