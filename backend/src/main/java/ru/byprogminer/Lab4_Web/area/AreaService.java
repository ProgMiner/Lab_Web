package ru.byprogminer.Lab4_Web.area;

import javax.ejb.Remote;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Remote
public interface AreaService extends Serializable {

    /**
     * Checks is point includes in the area
     *
     * @param x point's x, never null
     * @param y point's y, never null
     * @param r area's r, never null
     *
     * @return true if point includes, false otherwise
     */
    boolean checkPoint(
            @DecimalMin(value = "-5", inclusive = false) @DecimalMax(value = "3", inclusive = false)
            @NotNull BigDecimal x,
            @DecimalMin(value = "-5", inclusive = false) @DecimalMax(value = "3", inclusive = false)
            @NotNull BigDecimal y,
            @DecimalMin(value = "-5", inclusive = false) @DecimalMax(value = "3", inclusive = false)
            @NotNull BigDecimal r
    );
}
