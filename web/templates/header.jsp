<%--suppress CssUnusedSymbol --%><%--
--%><%@ page import="ru.byprogminer.Lab2_Web.AreaRenderer" %><%--
--%><%@ page import="ru.byprogminer.Lab2_Web.model.CompModel" %><%--
--%><%@ page import="ru.byprogminer.Lab2_Web.ControllerServlet" %><%--
--%><%@ page import="ru.byprogminer.Lab2_Web.Utility" %><%--
--%><%@ page contentType="text/html;charset=UTF-8" %><%--
--%><%
    if (request.getAttribute("LAB2_WEB") == null) return;
    final String baseUrl = Utility.getBaseUrl(request);
%><%--
--%><!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lab1_Web</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="apple-touch-icon" sizes="180x180" href="<%=request.getContextPath()%>/assets/favicon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="<%=request.getContextPath()%>/assets/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="194x194" href="<%=request.getContextPath()%>/assets/favicon/favicon-194x194.png">
    <link rel="icon" type="image/png" sizes="192x192" href="<%=request.getContextPath()%>/assets/favicon/android-chrome-192x192.png">
    <link rel="icon" type="image/png" sizes="16x16" href="<%=Utility.inlineImage(baseUrl, request.getContextPath() + "/assets/favicon/favicon-16x16.png")%>">
    <link rel="mask-icon" href="<%=request.getContextPath()%>/assets/favicon/safari-pinned-tab.svg" color="#5bbad5">
    <link rel="manifest" href="<%=request.getContextPath()%>/assets/favicon/site.webmanifest">
    <meta name="msapplication-TileColor" content="#603cba">
    <meta name="theme-color" content="#ffffff">

    <style>
        html, body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(32deg, #234523, #e0df3a, #f43458, #d0f89e) fixed;
            position: relative;
            color: #111;
            cursor: wait;
            font-size: 16pt;
            min-width: 690px;
        }

        a, button, input, label {
            cursor: cell;
        }

        label {
            user-select: none;
        }

        label:not([for]) {
            border: solid 1px rgba(255, 79, 163, 0.3);
            background: rgba(255, 79, 163, 0.2);
            white-space: nowrap;
            padding: 0 2px;
        }

        button, input:not([type="radio"]) {
            box-sizing: border-box;
            width: 100%;
        }

        .header, .header h1, .header h2, .header h3, .header h4, .header h5, .header h6 {
            font-family: serif;
            font-weight: bold;
            color: #111;
        }

        .header {
            font-size: 14pt;
        }

        .horizontal-rainbow {
            background: linear-gradient(to right, #F00, #FFA500, #FF0, #0F0, #0FF, #00F, #F0F);
        }

        .black-bg {
            background: #111;
            color: #fff;
        }

        .area {
            pointer-events: none;
            user-select: none;
        }

        .fancy-box {
            display: inline-block;
            border: solid 2px #ff69b4;
            padding: 10px;
            margin: 0 auto;

            margin-inline-start: 0.25ch;
            margin-inline-end: 0.25ch;
        }

        table.framework {
            border-collapse: collapse;
        }

        table.framework td {
            padding-bottom: 0;
            border-bottom: 0;
            padding-top: 0;
            border-top: 0;

            padding-inline-start: 0.25ch;
            padding-inline-end: 0.25ch;
            border-inline-start: 0;
            border-inline-end: 0;
        }

        table.framework td:first-child {
            padding-inline-start: 0;
        }

        table.framework td:last-child {
            padding-inline-end: 0;
        }

        table.bordered {
            border-collapse: collapse;
        }

        table.bordered td, table.bordered th {
            border: solid 1px #db7093;
            padding: 3px 10px;
        }

        table.bordered td,
        table.bordered tr:first-child th {
            border-top: none;
        }

        table.bordered td:first-child,
        table.bordered th:first-child {
            border-left: none;
        }

        table.bordered td,
        table.bordered tr:last-child th {
            border-bottom: none;
        }

        table.bordered td:last-child,
        table.bordered th:last-child {
            border-right: none;
        }

        .form-error-container:not(.shown-form-error-container) {
            display: none;
        }

        .bad-content {
            padding: 1px;
            border: solid 1px #999;
            background: #111;
            color: #fff;
        }

        button.selected-button {
            font-weight: bold;
            color: #ff0000;
        }

        [data-oaoaoa~="align-justify"] {
            text-align: justify;
        }

        [data-oaoaoa~="align-right"] {
            text-align: right;
        }

        [data-oaoaoa~="ib"] {
            display: inline-block;
        }

        [data-oaoaoa~="drop"] {
            transform: rotate(-155deg);
            transform-origin: 80% 75%;
        }

        [data-oaoaoa~="skew"] {
            transform: skew(-20deg);
        }

        [data-oaoaoa~="bg-text"] {
            -webkit-background-clip: text;
            color: transparent;
        }

        [data-oaoaoa~="small-text"] {
            font-size: 0.7em;
        }

        a[data-oaoaoa~="invisible"] {
            text-decoration: inherit;
            color: inherit;
        }

        #background {
            background-color: rgba(255, 255, 255, 0.74);
            position: fixed;
            top: 0;
            left: 20%;
            right: 20%;
            bottom: 0;
            z-index: -1;
        }

        #background::before, #background::after {
            content: '';
            position: absolute;
            top: 0;
            bottom: 0;
            z-index: -1;
        }

        #background::before {
            background-image: linear-gradient(to right, transparent, rgba(255, 255, 255, 0.74));
            left: -8%;
            right: 100%;
        }

        #background::after {
            background-image: linear-gradient(to left, transparent, rgba(255, 255, 255, 0.74));
            left: 100%;
            right: -8%;
        }

        #body {
            height: 100%;
            position: relative;
            z-index: 1;
        }

        #main {
            width: 60%;
            margin: 0 auto;
            text-align: center;
        }

        #form-error-container {
            border: solid 2px #8b0000;
        }

        #jepa {
            transform-origin: 100% 100%;
            transition: 0.1s;
            position: fixed;
            right: 0;
            bottom: 0;
            padding: 15px;
            z-index: 1000;
        }

        #jepa > img {
            transition: inherit;
            transform-origin: 50% 50%;
            position: relative;
            opacity: 1;
        }

        #jepa:hover {
            transform: scaleX(12) scaleY(6);
        }

        #jepa:hover > img {
            transform: rotate(1080deg) scaleY(2);
        }

        #rocket {
            position: fixed;
            z-index: 1000;
            bottom: 0;
            left: 0;
            top: -100%;
            right: -25%;
            background-image: url("<%=request.getContextPath()%>/assets/images/25568165.png");
            background-position: 100% 0;
            background-size: cover;
            transform: scale(0);
            transform-origin: 0 100%;
        }

        #rocket.flying-rocket {
            transition: 2s cubic-bezier(1, 0, 1, 1);
            transform: scale(1);
        }

        @media(max-width: 920px) {
            #background {
                left: 10%;
                right: 10%;
            }

            #main {
                width: 80%;
            }
        }
    </style>
</head>
<body>
<div id="body">
    <div id="background"></div>

    <table id="main">
        <tr class="header">
            <td>
                <h1>
                    <a href="<%=request.getContextPath()%>/" data-oaoaoa="invisible">Лабораторная работа №II</a><br />
                    <span class="black-bg" data-oaoaoa="ib skew" style="font-family: Impact, serif;">по <span class="horizontal-rainbow" data-oaoaoa="bg-text">Веб-программированию</span></span><br />
                    <span style="opacity: 0.1; margin-right: 273px;">Программированию Интернет Приложений</span>
                </h1>
            </td>
        </tr>

        <tr class="header">
            <td>
                <h2>
                    Вариант
                    <span data-oaoaoa="ib" style="transform: scaleX(-1);">№</span><!--
                 --><span data-oaoaoa="ib" style="transform: rotate(-4deg);">211</span><!--
                 --><span data-oaoaoa="ib" style="transform: rotate(4deg);">025</span>
                </h2>
            </td>
        </tr>

        <tr class="header">
            <td style="position: relative;">
                <% final CompModel compModel = (CompModel) request.getAttribute("compModel");
                   if (compModel.isResultAvailable()) { %>
                    <canvas id="area-canvas" class="area" width="205" height="205"
                            style="background: url('<%=Utility.inlineImage(baseUrl, request.getContextPath() + ControllerServlet.AREAS_IMAGE_PATH)%>');">
                        <img src="<%=request.getAttribute("areaUrl")%>" alt="Area" />
                    </canvas>

                    <script type="text/javascript">
                        (function() {
                            const canvas = document.getElementById("area-canvas");
                            const context = canvas.getContext("2d");

                            <% final AreaRenderer.Calculator areaCalc = new AreaRenderer.Calculator(205, 205, compModel.getR().doubleValue()); %>

                            context.fillStyle = "#ff0000";
                            context.beginPath();
                            context.arc(<%=areaCalc.translateX(compModel.getX().doubleValue()) + 0.5%>, <%=areaCalc.translateY(compModel.getY().doubleValue()) + 0.5%>, 2, 0, 360);
                            context.fill();
                        })();
                    </script>
                <% } else { %><img src="<%=request.getAttribute("areaUrl")%>" alt="Area" class="area" /><% } %>

                <h3 style="margin-right: -46px; transform: rotate(1deg); position: absolute; right: 0;" data-oaoaoa="ib align-right">
                    Доморацкого&nbsp;&nbsp;<br />
                    Эридана <span data-oaoaoa="ib drop">А</span>лексеевича&nbsp;<br />
                    Группа: P3<span style="font-size: 22pt; line-height: 32pt; vertical-align: middle;">2</span>11
                </h3>
            </td>
        </tr>
