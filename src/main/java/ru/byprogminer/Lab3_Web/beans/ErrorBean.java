package ru.byprogminer.Lab3_Web.beans;

import javax.faces.context.FacesContext;

public class ErrorBean {

    private String message;

    public ErrorBean() { message = null; }

    public ErrorBean(String message) {
        this.message = message;
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
