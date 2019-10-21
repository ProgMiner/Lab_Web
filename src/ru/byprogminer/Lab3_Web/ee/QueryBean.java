package ru.byprogminer.Lab3_Web.ee;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.math.BigDecimal;
import java.util.Arrays;

public class QueryBean {

    public final static String[] AVAILABLE_X = new String[] { "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4" };
    public final static double[] AVAILABLE_Y = new double[] { -3, 5 };
    public final static String[] AVAILABLE_R = new String[] {
            "1", "1.25", "1.5", "1.75",
            "2", "2.25", "2.5", "2.75",
            "3", "3.25", "3.5", "3.75",
            "4"
    };

    private BigDecimal x, y, r;

    public QueryBean() {
        x = y = r = null;
    }

    public QueryBean(BigDecimal x, BigDecimal y, BigDecimal r) {
        this.x = x;
        this.y = y;
        this.r = r;
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

    public String[] getAvailableX() {
        return AVAILABLE_X;
    }

    public double[] getAvailableY() {
        return AVAILABLE_Y;
    }

    public String[] getAvailableR() {
        return AVAILABLE_R;
    }

    public void validateX(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return;
        }

        if (!Arrays.asList(AVAILABLE_X).contains(value.toString())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "X must be one of following: " + String.join(", ", AVAILABLE_X), null));
        }
    }

    public void validateY(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return;
        }

        try {
            final BigDecimal val = new BigDecimal(value.toString().replace(',', '.'));

            if (val.compareTo(BigDecimal.valueOf(AVAILABLE_Y[0])) <= 0 ||
                    val.compareTo(BigDecimal.valueOf(AVAILABLE_Y[1])) >= 0)
            {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Y must be in (" + AVAILABLE_Y[0] + ", " + AVAILABLE_Y[1] + ")", null));
            }
        } catch (NumberFormatException e) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Y must be a number", null), e);
        }
    }

    public void validateR(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return;
        }

        if (!Arrays.asList(AVAILABLE_R).contains(value.toString())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "R must be one of following: " + String.join(", ", AVAILABLE_R), null));
        }
    }
}
