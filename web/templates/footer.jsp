<%@ page import="ru.byprogminer.Lab2_Web.Utility" %><%--
--%><%@ page import="ru.byprogminer.Lab2_Web.model.MainModel" %><%--
--%><%@ page import="java.time.ZonedDateTime" %><%--
--%><%@ page import="java.time.format.DateTimeFormatter" %><%--
--%><%@ page import="java.time.format.TextStyle" %><%--
--%><%@ page import="java.util.Locale" %><%--
--%><%@ page contentType="text/html;charset=UTF-8" %><%--
--%><% if (request.getAttribute("LAB2_WEB") == null) return; %>
            <tr>
                <td>
                    <div class="fancy-box" data-oaoaoa="small-text">
                        <table>
                            <tr>
                                <td>Current time:</td>
                                <td data-oaoaoa="align-justify" id="current-time">
                                    <% final MainModel mainModel = (MainModel) request.getAttribute("mainModel");
                                       final ZonedDateTime currentTime = mainModel.getCurrentTime(); %>
                                    <%=currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))%>
                                    <%=currentTime.getZone().getDisplayName(TextStyle.FULL, Locale.getDefault())%>
                                </td>
                            </tr>
                            <tr>
                                <td>Elapsed time:</td>
                                <td data-oaoaoa="align-justify"><%=String.format("%.15f", mainModel.getElapsedTime())%> ms.</td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>

            <tr><td><br />&nbsp;&nbsp;&nbsp;<a href="http://ifmo.ru/" target="_blank"><img src="<%=Utility.inlineImage(baseUrl, request.getContextPath() + "/assets/images/itmo-logo.png")%>" alt="IT's More Than a University!" style="display: inline-block; transform: rotate(2deg);" /></a></td></tr>
        </table>

        <div id="jepa">
            <!--suppress CheckImageSize -->
            <img src="<%=request.getContextPath()%>/assets/images/1-12676-512.png"
                 style="display: inline-block; background: url('<%=Utility.inlineImage(baseUrl, request.getContextPath() + "/assets/images/1-12676-128.png")%>');"
                 width="128" height="128" alt="" />
        </div>

        <div id="rocket"></div>
    </div>

    <% if (mainModel.doFrontendTimeUpdate()) { %>
        <script type="text/javascript">
            "use strict";

            const currentTimeCell = document.getElementById("current-time");
            const currentTimeInterval = setInterval(function() {
                const date = new Date();

                const offset = -date.getTimezoneOffset() / 60;
                currentTimeCell.innerText = date.getFullYear() + '-' + complete(date.getMonth() + 1) + '-' + complete(date.getDate()) + ' ' +
                    complete(date.getHours()) + ':' + complete(date.getMinutes()) + ':' + complete(date.getSeconds()) + ' ' +
                    'UTC' + (offset > 0 ? '+' : '') + (offset !== 0 ? offset : '');
            }, 500);

            function complete(src, length = 2, char = '0') {
                src = src + '';

                while (src.length < length) {
                    src = char + src;
                }

                return src;
            }
        </script>
    <% } %>
</body>
</html>
