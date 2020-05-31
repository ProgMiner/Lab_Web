package ru.byprogminer.Lab4_Web.queriesareacalculator;

import javax.ejb.Remote;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Remote
public interface QueriesAreaCalculator {

    void addPoint(@NotNull BigDecimal x, @NotNull BigDecimal y);
}
