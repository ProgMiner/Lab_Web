package ru.byprogminer.Lab3_Web.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/index.xhtml")
public class IndexUrlFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException
    {
        if ((req.getContextPath() + "/index.xhtml").equals(req.getRequestURI())) {
            res.sendRedirect(req.getContextPath());
        }

        chain.doFilter(req, res);
    }
}
