package ru.byprogminer.Lab2_Web;

import ru.byprogminer.Lab2_Web.model.CompModel;
import ru.byprogminer.Lab2_Web.model.CompModelImpl;
import ru.byprogminer.Lab2_Web.model.MainModel;
import ru.byprogminer.Lab2_Web.model.MainModelImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public class Factory {

    private final HttpServletRequest request;

    public Factory(HttpServletRequest request) {
        this.request = request;
    }

    public MainModel makeMainModel() {
        return makeMainModel(null);
    }

    public MainModel makeMainModel(Boolean doFrontendTimeUpdate) {
        return makeMainModel(doFrontendTimeUpdate, null);
    }

    public MainModel makeMainModel(Boolean doFrontendTimeUpdate, Long startTime) {
        final MainModelImpl model = new MainModelImpl(startTime);

        if (doFrontendTimeUpdate == null) {
            String newDoFrontendTimeUpdate = null;

            final Cookie cookie = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[] {}))
                    .filter(c -> c.getName().equals("doFrontendTimeUpdate"))
                    .findFirst().orElse(null);
            if (cookie != null) {
                newDoFrontendTimeUpdate = cookie.getValue();
            }

            final String param = request.getParameter("doFrontendTimeUpdate");
            if (param != null) {
                newDoFrontendTimeUpdate = param;
            }

            doFrontendTimeUpdate = Utility.parseBoolean(newDoFrontendTimeUpdate, doFrontendTimeUpdate);
        }

        if (doFrontendTimeUpdate != null) {
            model.setDoFrontendTimeUpdate(doFrontendTimeUpdate);
        }

        return model;
    }

    public CompModel makeCompModel() {
        return makeCompModel(null, null, null);
    }

    public CompModel makeCompModel(Integer x, Double y, Integer r) {
        if (x == null) {
            try {
                Integer newX = null;

                final String param = request.getParameter("x");
                if (param != null) {
                    newX = Integer.parseInt(param);
                }

                x = newX;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        if (y == null) {
            try {
                Double newY = null;

                final String param = request.getParameter("y");
                if (param != null) {
                    newY = Double.parseDouble(param);
                }

                y = newY;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        if (r == null) {
            try {
                Integer newR = null;

                final String param = request.getParameter("r");
                if (param != null) {
                    newR = Integer.parseInt(param);
                }

                r = newR;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return new CompModelImpl(x, y, r);
    }
}
