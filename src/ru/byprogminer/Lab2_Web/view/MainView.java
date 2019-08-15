package ru.byprogminer.Lab2_Web.view;

import ru.byprogminer.Lab2_Web.View;
import ru.byprogminer.Lab2_Web.model.MainModel;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class MainView implements View {

    private final MainModel model;
    private final AreaView areaView;
    private final CompView compView;

    public MainView(MainModel model, AreaView areaView, CompView compView) {
        this.model = model;
        this.areaView = areaView;
        this.compView = compView;
    }

    @Override
    public void render(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.setAttribute("MainView.model", model);
        request.setAttribute("MainView.areaView", areaView);
        request.setAttribute("MainView.compView", compView);

        request.getRequestDispatcher("/templates/main.jsp").forward(request, response);
    }
}
