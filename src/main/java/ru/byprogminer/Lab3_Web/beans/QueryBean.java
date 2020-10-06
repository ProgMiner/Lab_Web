package ru.byprogminer.Lab3_Web.beans;

import ru.byprogminer.Lab3_Web.Query;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.validator.ValidatorException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

public class QueryBean {

    public static final String[] AVAILABLE_X = new String[] { "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4" };
    public static final double[] AVAILABLE_Y = new double[] { -3, 5 };
    public static final String[] AVAILABLE_R = new String[] { "1", "1.25", "1.5", "1.75",
            "2", "2.25", "2.5", "2.75", "3", "3.25", "3.5", "3.75", "4" };

    private final Query query;
    private HistoryBean historyBean;
    private ErrorBean errorBean;

    public QueryBean() {
        query = new Query();
    }

    public QueryBean(BigDecimal x, BigDecimal y, BigDecimal r, Boolean result) {
        this.query = new Query(x, y, r, result);
    }

    public Object action() {
        historyBean.updateHistory(query);

        return null;
    }

    public void afterPhase(PhaseEvent event) throws IOException {
        if (event.getPhaseId() != PhaseId.PROCESS_VALIDATIONS) {
            return;
        }

        final Iterator<FacesMessage> messages = event.getFacesContext().getMessages();
        if (!messages.hasNext()) {
            return;
        }

        errorBean.setMessage(messages.next().getSummary());
        errorBean.error();

        event.getFacesContext().getExternalContext().redirect("error.xhtml");
    }

    public Boolean getResult() {
        return query.getResult();
    }

    public String getPointsJson() {
        return '[' + historyBean.getQueries().parallelStream()
                .map(q -> String.format("{\"x\": %s, \"y\": %s, \"r\": %s, \"result\": %s}",
                        q.getX(), q.getY(), q.getR(), q.getResult()))
                .collect(Collectors.joining(", ")) + ']';
    }

    public void validateX(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return;
        }

        if (!Arrays.asList(AVAILABLE_X).contains(value.toString())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, (String) component.getAttributes()
                    .getOrDefault("validatorMessage", "X must be one of following: " + String.join(", ", AVAILABLE_X)), null));
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
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, (String) component.getAttributes()
                        .getOrDefault("validatorMessage", "Y must be in (" + AVAILABLE_Y[0] + ", " + AVAILABLE_Y[1] + ")"), null));
            }
        } catch (NumberFormatException e) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, (String) component
                    .getAttributes().getOrDefault("converterMessage", "Y must be a number"), null), e);
        }
    }

    public void validateR(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return;
        }

        if (!Arrays.asList(AVAILABLE_R).contains(value.toString())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, (String) component.getAttributes()
                    .getOrDefault("validatorMessage", "R must be one of following: " + String.join(", ", AVAILABLE_R)), null));
        }
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

    public void setHistoryBean(HistoryBean historyBean) {
        this.historyBean = historyBean;
    }

    public void setErrorBean(ErrorBean errorBean) {
        this.errorBean = errorBean;
    }

    public String[] getAvailableX() {
        return AVAILABLE_X;
    }

    public String[] getAvailableR() {
        return AVAILABLE_R;
    }
}
