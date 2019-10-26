package ru.byprogminer.Lab3_Web.ee;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import java.io.IOException;

public class ErrorBean {

    private String message;

    public ErrorBean() { message = null; }

    public ErrorBean(String message) {
        this.message = message;
    }

    public void beforePhase(PhaseEvent event) throws IOException {
        if (event.getPhaseId() != PhaseId.RESTORE_VIEW) {
            return;
        }

        if (!event.getFacesContext().getExternalContext().getFlash().containsKey("error")) {
            event.getFacesContext().getExternalContext().redirect("/");
        }
    }

    public String error() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().putNow("error", this);
        return "error";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
