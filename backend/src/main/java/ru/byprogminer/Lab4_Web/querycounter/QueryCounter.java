package ru.byprogminer.Lab4_Web.querycounter;

import javax.ejb.Remote;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Remote
public interface QueryCounter {

    void addPointCheckingResult(boolean result);
    void sendPointCheckingValidationFailure(@NotNull BigDecimal x, @NotNull BigDecimal y, @NotNull BigDecimal r);
}
