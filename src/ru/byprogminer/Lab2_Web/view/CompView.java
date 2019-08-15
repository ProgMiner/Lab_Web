package ru.byprogminer.Lab2_Web.view;

import ru.byprogminer.Lab2_Web.View;
import ru.byprogminer.Lab2_Web.model.CompModel;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CompView implements View {

    private final CompModel model;

    public CompView(CompModel model) {
        this.model = model;
    }

    @Override
    public void render(ServletRequest request, ServletResponse response) {
        // TODO
    }
}
