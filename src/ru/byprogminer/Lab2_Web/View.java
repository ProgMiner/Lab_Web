package ru.byprogminer.Lab2_Web;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public interface View {

    void render(ServletRequest request, ServletResponse response) throws ServletException, IOException;
}
