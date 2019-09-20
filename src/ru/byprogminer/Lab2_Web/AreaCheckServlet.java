package ru.byprogminer.Lab2_Web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/" + AreaCheckServlet.URL)
public class AreaCheckServlet extends HttpServlet {

    public static final String URL = "area-check";

    private static final String RESULT_JSP_URL = "templates/result.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (ControllerServlet.isSecurityAttributeNotSet(request)) {
            request.getRequestDispatcher(ControllerServlet.URL).forward(request, response);
            return;
        }

        request.getRequestDispatcher(RESULT_JSP_URL).forward(request, response);
    }
}
