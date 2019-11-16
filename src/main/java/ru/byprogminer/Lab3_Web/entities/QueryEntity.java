package ru.byprogminer.Lab3_Web.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "history")
public class QueryEntity {

    @Id @GeneratedValue private long id;
    @Column(precision = 25, scale = 20) private BigDecimal x;
    @Column(precision = 25, scale = 20) private BigDecimal y;
    @Column(precision = 25, scale = 20) private BigDecimal r;
    private boolean result;

    public QueryEntity() {}

    public QueryEntity(BigDecimal x, BigDecimal y, BigDecimal r, boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getR() {
        return r;
    }

    public void setR(BigDecimal r) {
        this.r = r;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
