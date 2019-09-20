package ru.byprogminer.Lab2_Web;

import ru.byprogminer.Lab2_Web.model.CompModel;
import ru.byprogminer.Lab2_Web.utility.AreaRenderer;
import ru.byprogminer.Lab2_Web.utility.Factory;
import ru.byprogminer.Lab2_Web.utility.JspUtility;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;

@WebServlet("/" + ControllerServlet.URL)
public class ControllerServlet extends HttpServlet {

    public static final String APP_NAME = "Lab2_Web";
    public static final String URL = "";

    public static final String AREAS_IMAGE_PATH = "/assets/images/areas.png";

    private static final String FORM_JSP_URL = "templates/form.jsp";
    private static final String SECURITY_ATTRIBUTE_NAME = "LAB2_WEB";
    private static final String HISTORY_ATTRIBUTE_NAME = "history";

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
                        getHistory(request.getSession()), compModel.getR()));

        if (compModel.isResultAvailable()) {
            request.getRequestDispatcher(AreaCheckServlet.URL).forward(request, response);
        } else {
            request.getRequestDispatcher(FORM_JSP_URL).forward(request, response);
        }
    }

    private void updateHistory(HttpSession session, CompModel compModel) {
        final Deque<HistoryNode> history = getHistory(session);
        final HistoryNode previous = history.peekFirst();

        final BigDecimal x = compModel.getX();
        final BigDecimal y = compModel.getY();
        final BigDecimal r = compModel.getR();

        //noinspection SuspiciousNameCombination
        if (previous == null ||
                !previous.x.equals(x) ||
                !previous.y.equals(y) ||
                !previous.r.equals(r)) {
            history.addFirst(new HistoryNode(x, y, r, compModel.getResult(x, y, r)));
        }
    }

    public static boolean isSecurityAttributeNotSet(ServletRequest request) {
        return request.getAttribute(SECURITY_ATTRIBUTE_NAME) == null;
    }

    public static Deque<HistoryNode> getHistory(HttpSession session) {
        final Object history = session.getAttribute(HISTORY_ATTRIBUTE_NAME);

        if (
                history instanceof Deque &&
                !((Deque) history).isEmpty() &&
                ((Deque) history).peek() instanceof HistoryNode
        ) {
            //noinspection unchecked
            return (Deque<HistoryNode>) history;
        }

        final Deque<HistoryNode> newHistory = new LinkedList<>();
        session.setAttribute(HISTORY_ATTRIBUTE_NAME, newHistory);

        return newHistory;
    }
}
