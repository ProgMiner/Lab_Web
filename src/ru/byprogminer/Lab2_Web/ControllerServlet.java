package ru.byprogminer.Lab2_Web;

import ru.byprogminer.Lab2_Web.model.CompModel;
import ru.byprogminer.Lab2_Web.utility.AreaRenderer;
import ru.byprogminer.Lab2_Web.utility.Factory;
import ru.byprogminer.Lab2_Web.utility.JspUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(ControllerServlet.URL)
public class ControllerServlet extends HttpServlet {

    public static final String APP_NAME = "Lab2_Web";

    public static final String SECURITY_ATTRIBUTE_NAME = "LAB2_WEB";
    public static final String HISTORY_ATTRIBUTE_NAME = "history";
    public static final String AREAS_IMAGE_PATH = "/assets/images/areas.png";
    public static final String URL = "";

    private static final String FORM_JSP_URL = "templates/form.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(SECURITY_ATTRIBUTE_NAME, new Object());

        final Factory factory = new Factory(request);
        final CompModel compModel = factory.makeCompModel();

        request.setAttribute("mainModel", factory.makeMainModel());
        request.setAttribute("compModel", compModel);

        if (compModel.isResultAvailable()) {
            updateHistory(request.getSession(), compModel);
        }

        request.setAttribute("areaUrl", new AreaRenderer(request.getServletContext())
                .renderArea(AREAS_IMAGE_PATH, new JspUtility(request).inlineImage(AREAS_IMAGE_PATH),
                        (HistoryNode) request.getSession().getAttribute(HISTORY_ATTRIBUTE_NAME)));

        if (compModel.isResultAvailable()) {
            request.getRequestDispatcher(AreaCheckServlet.URL).forward(request, response);
        } else {
            request.getRequestDispatcher(FORM_JSP_URL).forward(request, response);
        }
    }

    private void updateHistory(HttpSession session, CompModel compModel) {
        BigDecimal x = compModel.getX();
        BigDecimal y = compModel.getY();
        BigDecimal r = compModel.getR();

        final HistoryNode previous = (HistoryNode) session.getAttribute(HISTORY_ATTRIBUTE_NAME);
        if (previous == null ||
                x.compareTo(previous.x) != 0 ||
                y.compareTo(previous.y) != 0 ||
                r.compareTo(previous.r) != 0) {
            session.setAttribute(HISTORY_ATTRIBUTE_NAME, new HistoryNode(x, y, r, compModel.getResult(x, y, r), previous));
        }
    }
}
