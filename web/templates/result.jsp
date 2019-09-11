<%@ page contentType="text/html;charset=UTF-8" %><%--
--%><%@ include file="header.jsp" %><%--
--%><% final BigDecimal x = compModel.getX();
       final BigDecimal y = compModel.getY();
       final BigDecimal r = compModel.getR(); %><%--
--%><tr><td>
    <table class="fancy-box">
        <tr>
            <th>X: </th>
            <td><%=x%></td>
        </tr>
        <tr>
            <th>Y: </th>
            <td style="word-break: break-all;"><%=y%></td>
        </tr>
        <tr>
            <th>R: </th>
            <td><%=r%></td>
        </tr>
        <tr>
            <th>Result: </th>
            <td data-oaoaoa="align-justify">point is<%=compModel.getResult(x, y, r) ? "" : "n't"%> included in the area</td>
        </tr>
    </table>
</td></tr>
<%@ include file="footer.jsp" %>
