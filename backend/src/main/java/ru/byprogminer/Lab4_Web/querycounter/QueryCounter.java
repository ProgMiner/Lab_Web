package ru.byprogminer.Lab4_Web.querycounter;

import javax.ejb.Remote;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Remote
public interface QueryCounter {

    void sendCheckPointResult(boolean result);
    void sendCheckPointValidationFailure(@NotNull BigDecimal x, @NotNull BigDecimal y, @NotNull BigDecimal r);
}
