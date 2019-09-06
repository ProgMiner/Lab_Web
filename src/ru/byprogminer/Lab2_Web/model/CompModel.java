package ru.byprogminer.Lab2_Web.model;

import java.math.BigDecimal;

public interface CompModel {

    boolean isResultAvailable();

    BigDecimal getX();
    BigDecimal getY();
    BigDecimal getR();

    boolean getResult(BigDecimal x, BigDecimal y, BigDecimal r);
}
