<%@ page import="ru.byprogminer.Lab2_Web.model.CompModelImpl" %><%--
--%><%@ page import="ru.byprogminer.Lab2_Web.model.MainModel" %><%--
--%><%@ page import="java.time.ZonedDateTime" %><%--
--%><%@ page import="java.time.format.DateTimeFormatter" %><%--
--%><%@ page import="java.time.format.TextStyle" %><%--
--%><%@ page import="java.util.Arrays" %><%--
--%><%@ page import="java.util.Locale" %><%--
--%><%@ page import="java.util.stream.Collectors" %><%--
--%><%@ page contentType="text/html;charset=UTF-8" %>
        <% if (!history.isEmpty()) { %>
            <tr><td>

                <table class="fancy-box bordered" style="padding-top: 0;">
                    <tr><th colspan="6">Results history</th></tr>
                    <tr>
                        <th colspan="1">X</th>
                        <th>Y</th>
                        <th>R</th>
                        <th>Result</th>
                    </tr>
                    <% for (HistoryNode historyNode : history) { %>
                        <tr>
                            <td><%=historyNode.x%></td>
                            <td style="word-break: break-all;"><%=historyNode.y%></td>
                            <td><%=historyNode.r%></td>
                            <td style="white-space: nowrap;"><%=historyNode.result ? "" : "not"%> included</td>
                        </tr>
                    <% } %>
                </table>
            </td></tr>
        <% } %>

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

        <tr>
            <td>
                <br>
                &nbsp;&nbsp;&nbsp;
                <a href="http://ifmo.ru/" target="_blank">
                    <img src="<%=utility.inlineImage("/assets/images/itmo-logo.png")%>" alt="IT's More Than a University!" style="display: inline-block; transform: rotate(2deg);">
                </a>
            </td>
        </tr>
    </table>

    <div id="jepa">
        <!--suppress CheckImageSize -->
        <img src="<%=request.getContextPath()%>/assets/images/1-12676-512.png"
             style="display: inline-block; background: url('<%=utility.inlineImage("/assets/images/1-12676-128.png")%>');"
             width="128" height="128" alt="">
    </div>

    <div id="rocket"></div>
    <div id="background"></div>

    <script type="text/javascript">
        "use strict";

        <% if (compModel.isResultAvailable()) { %>
            <%--suppress JSDuplicatedDeclaration --%>
            const getR = () => <%=compModel.getR()%>;
        <% } else { %>
            <%--suppress JSDuplicatedDeclaration, UnreachableCodeJS --%>
            function getR(displayError = true) {
                const rInput = document.getElementById("r-input");
                const _displayFormError = displayError ? (error) => displayFormError(error) : () => {};

                if (rInput == null) {
                    _displayFormError("WTF");
                    return null;
                }

                const rValue = rInput.value;
                const r = parseInt(rValue);

                if (isNaN(r) || rValue !== r.toString()) {
                    _displayFormError("Cannot determine area zoom without specified R");
                    return null;
                }

                if (<%=Arrays.stream(CompModelImpl.ALLOWED_RS).mapToObj(r1-> "r !== " + r1).collect(Collectors.joining(" && "))%>) {
                    _displayFormError("Bad R specified");
                    return null;
                }

                return r;
            }
        <% } %>

        repaintArea(getR(false));
        Array.prototype.forEach.call(document.querySelectorAll(".area"), node => node.onclick = function(event) {
            const r = getR();

            if (r == null) {
                return false;
            }

            const rect = event.target.getBoundingClientRect();
            const width = rect.right - rect.left;
            const height = rect.bottom - rect.top;
            const x = event.clientX - rect.left;
            const y = event.clientY - rect.top;

            const centerX = Math.round(width / 2) - 1;
            const centerY = Math.round(height / 2) - 1;

            const zoomX = 80 * width / 205 / r;
            const zoomY = 80 * height / 205 / r;

            function sendForm(method, action, parameters) {
                const form = document.createElement("form");
                form.action = action;
                form.method = method;
                form.style.display = "hidden";

                for (let parameter in parameters) {
                    if (!parameters.hasOwnProperty(parameter)) {
                        continue;
                    }

                    const field = document.createElement("input");
                    field.type = "hidden";
                    field.name = parameter;
                    field.value = parameters[parameter];

                    form.appendChild(field);
                }

                document.body.appendChild(form);
                form.submit();
            }

            function findNearest(value, list) {
                let delta = Number.MAX_VALUE, val;

                Array.prototype.forEach.call(list, function(v) {
                    const currentDelta = Math.abs(value - v);

                    if (delta > currentDelta) {
                        delta = currentDelta;
                        val = v;
                    }
                });

                return val;
            }

            sendForm("GET", "<%=request.getContextPath()%>/", {
                x: findNearest((x - centerX) / zoomX, [<%=Arrays.stream(CompModelImpl.ALLOWED_XES).mapToObj(Integer::toString).collect(Collectors.joining(", "))%>]),
                y: Math.min(Math.max((centerY - y) / zoomY, <%=CompModelImpl.ALLOWED_YS_RANGE[0] + 0.00000001%>), <%=CompModelImpl.ALLOWED_YS_RANGE[1] - 0.00000001%>),
                r: r
            });
        });

        <% if (mainModel.doFrontendTimeUpdate()) { %>
            (function() {
                const currentTimeCell = document.getElementById("current-time");
                setInterval(function () {
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
            })();
        <% } %>
    </script>
</body>
</html>
