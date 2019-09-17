<%@ page contentType="text/html;charset=UTF-8" %><%--
--%><%@ include file="header.jsp" %><%--
--%><tr><td>
    <form action="<%=request.getContextPath()%>/" method="get" id="form" class="fancy-box">
        <table>
            <tr class="form-error-container">
                <td id="form-error-container" colspan="2"></td>
            </tr>
            <tr>
                <td>X: </td>
                <td>
                    <table style="width: 100%;" class="framework">
                        <tr>
                            <% final BigDecimal x = compModel.getX();
                               for (int currentX : CompModelImpl.ALLOWED_XES) { %>
                                <td><label><input type="radio" name="x" value="<%=currentX%>" <%=BigDecimal.valueOf(currentX).equals(x) ? "checked" : ""%>><%=currentX%> </label></td>
                            <% } %>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <% final BigDecimal y = compModel.getY(); %>
                <td><label for="y-input">Y: </label></td>
                <td><input type="text" name="y" id="y-input" <%=y != null ? "value=\"" + y + '"' : ""%>></td>
            </tr>
            <tr>
                <td>R: </td>
                <td>
                    <table style="width: 100%;" class="framework">
                        <tr>
                            <% final BigDecimal r = compModel.getR();
                               for (int currentR : CompModelImpl.ALLOWED_RS) { %>
                                <td><button name="r" value="<%=currentR%>" <%=BigDecimal.valueOf(currentR).equals(r) ? "class=\"selected-button\"" : ""%>><%=currentR%></button></td>
                            <% } %>
                        </tr>
                    </table>
                    <input type="hidden" name="r" id="r-input" <%=r != null ? "value=\"" + r + '"' : ""%>>
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
            Array.prototype.forEach.call(document.querySelectorAll(".form-error-container"), node => node.classList.add("shown-form-error-container"));
            document.getElementById("form-error-container").innerText = error;

            if (element !== undefined) {
                element.classList.add("bad-content");
            }
        }

        function hideFormError(element) {
            Array.prototype.forEach.call(document.querySelectorAll(".form-error-container"), node => node.classList.remove("shown-form-error-container"));

            if (element !== undefined) {
                element.classList.remove("bad-content");
            }
        }

        (function() {
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

                value = value.substring(0, 15);
                const floatValue = parseFloat(value);

                if (isNaN(floatValue)) {
                    displayFormError(name + " is not a number!", element);
                    return false;
                }

                if (floatValue <= min || floatValue >= max) {
                    displayFormError(name + " must be in (" + min + ", " + max + ")!", element);
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
            const validateY = (submit) => validateField(yInput.value, submit, yInput, "Y", <%=CompModelImpl.ALLOWED_YS_RANGE[0]%>, <%=CompModelImpl.ALLOWED_YS_RANGE[1]%>);
            const validateR = (submit) => validateChecked(submit, rButtons, "R", (btn) => btn.value === rInput.value);

            Array.prototype.forEach.call(xRadios, node => node.onclick = () => validateX(false));
            yInput.oninput = () => validateY(false);

            Array.prototype.forEach.call(rButtons, node => node.onclick = function () {
                Array.prototype.forEach.call(rButtons, node => node.classList.remove("selected-button"));

                const newValue = node.value === rInput.value ? null : node.value;
                if (newValue !== null) {
                    node.classList.add("selected-button");
                }

                repaintArea(newValue);
                validateR(rInput.value = newValue, false);
                return false;
            });

            const form = document.getElementById("form");

            let animStarted = false;
            form.onsubmit = function () {
                if (animStarted) {
                    return true;
                }

                if (validateX(true) && validateY(true) && validateR(true)) {
                    document.body.style.pointerEvents = "none";
                    setTimeout(() => form.submit(), 1700);

                    document.getElementById("rocket").classList.add("flying-rocket");
                }

                return false;
            };
        })();
    </script>
</td></tr>
<%@ include file="footer.jsp" %>
