package ru.byprogminer.Lab4_Web.history;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final QueryEntity that = (QueryEntity) o;
        return userId == that.userId &&
                result == that.result &&
                Objects.equals(id, that.id) &&
                Objects.equals(x, that.x) &&
                Objects.equals(y, that.y) &&
                Objects.equals(r, that.r);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, x, y, r, result);
    }
}
