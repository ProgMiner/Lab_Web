package ru.byprogminer.Lab3_Web.ee;

import ru.byprogminer.Lab3_Web.Query;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.math.BigDecimal;
import java.util.Arrays;

public class QueryBean {

    public static final String[] AVAILABLE_X = new String[] { "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4" };
    public static final double[] AVAILABLE_Y = new double[] { -3, 5 };
    public static final String[] AVAILABLE_R = new String[] {
            "1", "1.25", "1.5", "1.75",
            "2", "2.25", "2.5", "2.75",
            "3", "3.25", "3.5", "3.75",
            "4"
    };

    private final Query query;

    public QueryBean() {
        query = new Query();
    }

    public QueryBean(BigDecimal x, BigDecimal y, BigDecimal r, Boolean result) {
        this.query = new Query(x, y, r, result);
    }

    public Object action() {
        return "";
    }

    public Boolean getResult() {
        if (query.getResult() == null) {
            query.setResult(doGetResult());
        }

        return query.getResult();
    }

    private Boolean doGetResult() {
        BigDecimal x = query.getX();
        BigDecimal y = query.getY();
        BigDecimal r = query.getR();

        if (x == null || y == null || r == null) {
            return null;
        }

        return batman(x.doubleValue(), y.doubleValue(), r.doubleValue());
    }

    // https://github.com/ejller/Web_two/blob/39cc75c069e79ecaecc8e501039b1e8bdb1966f7/ServletExampl-2/src/main/java/com/devcolibri/servlet/AreaCheckServlet.java#L85
    private boolean batman(double x, double y, double r) {
//if ax == 4, Rx == 28 ==> 28/4

//if ay == 4, Ry == 12 => coef = 4/12 = 1/3
        double rx = r / 7.0;
        double ry = r / 6.0;
        final double x1 = (Math.pow(x, 2)) / (49 * Math.pow(rx, 2)) + (Math.pow(y, 2)) / (9 * Math.pow(ry, 2)) - 1.0;
        boolean elipce = x1 <= 0.00000000000001;
        System.out.println(x1);
        boolean elipce_down_x = (Math.abs(x / rx)) >= 4;
        boolean elipce_down_y = (y / ry >= -3 * Math.sqrt(33) / 7.0) && (y / ry <= 0);
        boolean elipce_up_x = (Math.abs(x / rx)) >= 3;
        boolean elipce_up_y = y >= 0;
        System.out.println("Elipce " + elipce);
        System.out.println("Elipce elipce_down_x " + elipce_down_x);
        System.out.println("Elipce elipce_down_y " + elipce_down_y);
        System.out.println("Elipce elipce_up_x " + elipce_up_x);
        System.out.println("Elipce elipce_up_y " + elipce_up_y);
        boolean full_elipce = (elipce & elipce_down_x & elipce_down_y) || (elipce & elipce_up_x & elipce_up_y);
        //System.out.println("full elipce"+ full_elipce);

        boolean smile = (((3 * Math.sqrt(33) - 7) * Math.pow(x, 2)) / (-112.0 * Math.pow(rx, 2)) + Math.abs(x / rx) / 2
                + Math.sqrt(1 - Math.pow(Math.abs((Math.abs(x / rx)) - 2) - 1, 2)) - y / ry - 3) <= 0;
        boolean smile_y = (y / ry >= -3) && (y / ry <= 0);
        boolean smile_x = (x / ry <= 4) && (x / ry >= -4);

        boolean full_smile = smile & smile_x & smile_y;

        //System.out.println("full_smile "+full_smile);

        boolean ears_y = y >= 0;
        boolean ears_x = Math.abs(x / rx) <= 1 && Math.abs(x / rx) >= 0.75;
        boolean ears = -8 * Math.abs(x / rx) - y / ry + 9 >= 0;

        boolean full_ears = ears & ears_x & ears_y;
        //System.out.println("full_ears "+full_ears);

        boolean ears2_x = Math.abs(x / rx) <= 0.75 && Math.abs(x / rx) >= 0.5;
        boolean ears2 = 3 * Math.abs(x / rx) - y / ry + 0.75 >= 0;

        boolean full_ears2 = ears2 & ears2_x & ears_y;
        //System.out.println("full_ears2"+full_ears2);

        boolean chelka_y = y >= 0;
        boolean chelka_x = Math.abs(x / rx) <= 0.5;
        boolean chelka = 9.0 / 4.0 - y / ry >= 0;

        boolean chelka_full = chelka_x && chelka_y && chelka;

        boolean wings_x = Math.abs(x / rx) <= 3 && Math.abs(x / rx) >= 1;
        boolean wings_y = y >= 0;
        boolean wings = -Math.abs(x / rx) / 2 - (3.0 / 7.0) * Math.sqrt(10) * Math.sqrt(4 - Math.pow(Math.abs(x / rx) - 1, 2)) - y / ry + (6 * Math.sqrt(10)) / 7.0 + 1.5 >= 0;

        boolean full_wings = wings && wings_y && wings_x;
        //System.out.println("full_w"+full_wings);
        return full_elipce || full_smile || full_ears || full_ears2 || full_wings || chelka_full;
    }

    public BigDecimal getX() {
        return query.getX();
    }

    public void setX(BigDecimal x) {
        query.setX(x);
    }

    public BigDecimal getY() {
        return query.getY();
    }

    public void setY(BigDecimal y) {
        query.setY(y);
    }

    public BigDecimal getR() {
        return query.getR();
    }

    public void setR(BigDecimal r) {
        query.setR(r);
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
