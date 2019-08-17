<%@ page contentType="text/html;charset=UTF-8" %><%--
--%><% if (request.getAttribute(ControllerServlet.SECURITY_ATTRIBUTE_NAME) == null) return; %><%--
--%><%@ include file="header.jsp" %><%--
--%><% final double x = compModel.getX().doubleValue();
       final double y = compModel.getY().doubleValue();
       final double r = compModel.getR().doubleValue(); %><%--
--%><tr><td>
    <table class="fancy-box">
        <tr>
            <td>X: </td>
            <td><%=x%></td>
        </tr>
        <tr>
            <td>Y: </td>
            <td><%=y%></td>
        </tr>
        <tr>
            <td>R: </td>
            <td><%=r%></td>
        </tr>
        <tr>
            <td>Result: </td>
            <td data-oaoaoa="align-justify">point is<%=compModel.getResult(x, y, r) ? "" : "n't"%> included in the area</td>
        </tr>
    </table>
</td></tr>
<%@ include file="footer.jsp" %>
