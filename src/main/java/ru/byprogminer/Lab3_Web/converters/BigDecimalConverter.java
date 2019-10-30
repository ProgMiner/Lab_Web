package ru.byprogminer.Lab3_Web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class BigDecimalConverter extends javax.faces.convert.BigDecimalConverter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s != null) {
            s = s.replace(',', '.');
        }

        return super.getAsObject(facesContext, uiComponent, s);
    }
}
