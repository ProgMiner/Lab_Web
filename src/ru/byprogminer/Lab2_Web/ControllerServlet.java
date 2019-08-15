package ru.byprogminer.Lab2_Web;

import ru.byprogminer.Lab2_Web.model.CompModel;
import ru.byprogminer.Lab2_Web.view.AreaView;
import ru.byprogminer.Lab2_Web.view.CompView;
import ru.byprogminer.Lab2_Web.view.MainView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("LAB2_WEB", new Object());

        final Factory factory = new Factory(request);

        final CompModel compModel = factory.makeCompModel();
        new MainView(factory.makeMainModel(), new AreaView(compModel), new CompView(compModel)).render(request, response);
    }
}
