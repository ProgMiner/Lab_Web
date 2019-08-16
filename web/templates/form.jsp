<%@ page import="ru.byprogminer.Lab2_Web.HistoryNode" %><%--
--%><%@ page import="ru.byprogminer.Lab2_Web.model.CompModelImpl" %><%--
--%><%--
--%><%@ page contentType="text/html;charset=UTF-8" %><%--
--%><% if (request.getAttribute("LAB2_WEB") == null) return; %><%--
--%><%@ include file="header.jsp" %>
<form action="<%=request.getContextPath()%>/" method="get" id="form" class="fancy-box">
    <table>
        <tr class="form-error-container">
            <td id="form-error-container" colspan="2"></td>
        </tr>
        <tr>
            <td>X: </td>
            <td>
                <% final Number x = compModel.getX();
                   for (int currentX : CompModelImpl.ALLOWED_XES) { %>
                    <label><input type="radio" name="x" value="<%=currentX%>" <%=Integer.valueOf(currentX).equals(x) ? "checked" : ""%>/><%=currentX%> </label>
                <% } %><!--
         --></td>
        </tr>
        <tr>
            <% final Number y = compModel.getY(); %>
            <td><label for="y-input">Y: </label></td>
            <td><input type="text" name="y" id="y-input" <%=y != null ? "value=\"" + y + '"' : ""%> /></td>
        </tr>
        <tr>
            <td>R: </td>
            <td>
                <table style="width: 100%;">
                    <tr>
                        <% final Number r = compModel.getR();
                           for (int currentR : CompModelImpl.ALLOWED_RS) { %>
                            <td><button name="r" value="<%=currentR%>" <%=Integer.valueOf(currentR).equals(r) ? "checked" : ""%>><%=currentR%></button></td>
                        <% } %>
                    </tr>
                </table>
                <input type="hidden" name="r" id="r-input" <%=r != null ? "value=\"" + r + '"' : ""%> />
            </td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Поїхали!"></td>
        </tr>
    </table>
</form>

<script type="text/javascript">
    "use strict";

    function displayFormError(error, element) {
        document.querySelectorAll(".form-error-container").forEach(node => node.classList.add("shown-form-error-container"));
        document.getElementById("form-error-container").innerText = error;

        if (element !== undefined) {
            element.classList.add("bad-content");
        }
    }

    function hideFormError(element) {
        document.querySelectorAll(".form-error-container").forEach(node => node.classList.remove("shown-form-error-container"));

        if (element !== undefined) {
            element.classList.remove("bad-content");
        }
    }

    function validateChecked(submit, elements, name, filter) {
        if (Array.prototype.slice.call(elements).filter(filter).length > 0) {
            hideFormError();
            return true;
        }

        if (submit) {
            displayFormError("No one " + name + " value checked!");
        } else {
            hideFormError();
        }

        return false;
    }

    function validateField(value, submit, element, name, min, max) {
        if (value.trim() === "") {
            if (submit) {
                displayFormError(name + " cannot be empty!", element);
            } else {
                hideFormError(element);
            }

            return false;
        }

        const floatValue = parseFloat(value);

        if (floatValue.toString() !== value || isNaN(floatValue)) {
            displayFormError(name + " is not a number!", element);
            return false;
        }

        if (floatValue < min || floatValue > max) {
            displayFormError(name + " must be in [" + min + ", " + max + "]!", element);
            return false;
        }

        hideFormError(element);
        return true;
    }

    const xRadios = document.querySelectorAll("#form input[type=radio][name=x]");
    const yInput = document.getElementById("y-input");
    const rButtons = document.querySelectorAll("#form button[name=r]");
    const rInput = document.getElementById("r-input");

    const validateX = (submit) => validateChecked(submit, xRadios, "X", (cb) => cb.checked);
    const validateY = (value, submit) => validateField(value, submit, yInput, "Y", <%=CompModelImpl.ALLOWED_YS_RANGE[0]%>, <%=CompModelImpl.ALLOWED_YS_RANGE[1]%>);
    const validateR = (value, submit) => validateChecked(submit, rButtons, "R", (btn) => btn.value === rInput.value);

    xRadios.forEach((node) => node.onclick = () => validateX(false));
    rButtons.forEach((node) => node.onclick = function () {
        rButtons.forEach((node) => node.classList.remove("selected-button"));
        node.classList.add("selected-button");

        validateR(rInput.value = node.value, false);
        return false;
    });

    yInput.oninput = () => validateY(yInput.value, false);
    rInput.oninput = () => validateR(rInput.value, false);

    document.getElementById("form").onsubmit = () =>
        validateX(true) && validateY(yInput.value, true) && validateR(rInput.value, true);
</script>

<% final HistoryNode historyHead = (HistoryNode) request.getSession().getAttribute(ControllerServlet.HISTORY_ATTRIBUTE_NAME);
   if (historyHead != null) { %>
    </td></tr><tr><td>

    <table class="fancy-box bordered" style="padding-top: 0;">
        <tr><th colspan="6">Results history</th></tr>
        <tr>
            <th colspan="1">X</th>
            <th>Y</th>
            <th>R</th>
            <th>Result</th>
        </tr>
        <% HistoryNode historyNode = historyHead;
           while (historyNode != null) { %>
            <tr>
                <td><%=historyNode.x%></td>
                <td><%=historyNode.y%></td>
                <td><%=historyNode.r%></td>
                <td><%=historyNode.result ? "" : "not"%> included</td>
            </tr>
            <% historyNode = historyNode.next;
           } %>
    </table>
<% } %>
<%@ include file="footer.jsp" %>
