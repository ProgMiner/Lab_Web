package ru.byprogminer.Lab4_Web.area;

import javax.ejb.Remote;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;

@Remote
public interface AreaService extends Serializable {

    boolean checkPoint(
            @DecimalMin(value = "-5", inclusive = false) @DecimalMax(value = "3", inclusive = false) BigDecimal x,
            @DecimalMin(value = "-5", inclusive = false) @DecimalMax(value = "3", inclusive = false) BigDecimal y,
            @DecimalMin(value = "-5", inclusive = false) @DecimalMax(value = "3", inclusive = false) BigDecimal r
    );
}
