package ru.byprogminer.Lab2_Web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/assets/*")
public class AssetsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final InputStream is = getServletContext().getResourceAsStream(request.getRequestURI().substring(request.getContextPath().length()));
        final OutputStream os = response.getOutputStream();

        int b;
        while ((b = is.read()) != -1) {
            os.write(b);
        }
    }
}
