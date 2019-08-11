<?php if (!defined('LAB1_WEB')) die();

use Lab_Web\Utility; ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lab1_Web</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="apple-touch-icon" sizes="180x180" href="<?=Utility::url(__DIR__.'/../favicon/apple-touch-icon.png')?>">
    <link rel="icon" type="image/png" sizes="32x32" href="<?=Utility::url(__DIR__.'/../favicon/favicon-32x32.png')?>">
    <link rel="icon" type="image/png" sizes="194x194" href="<?=Utility::url(__DIR__.'/../favicon/favicon-194x194.png')?>">
    <link rel="icon" type="image/png" sizes="192x192" href="<?=Utility::url(__DIR__.'/../favicon/android-chrome-192x192.png')?>">
    <link rel="icon" type="image/png" sizes="16x16" href="<?=Utility::url(__DIR__.'/../favicon/favicon-16x16.png')?>">
    <link rel="mask-icon" href="<?=Utility::url(__DIR__.'/../favicon/safari-pinned-tab.svg')?>" color="#5bbad5">
    <link rel="manifest" href="<?=Utility::url(__DIR__.'/../favicon/site.webmanifest')?>">
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

        label + label {
            margin-inline-start: 0.5ch;
        }

        button, input:not([type="checkbox"]) {
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
            border: solid 2px hotpink;
            padding: 10px;
            margin: 0 auto;
        }

        .fancy-box + .fancy-box {
            margin-inline-start: 0.5ch;
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
            border: solid 2px darkred;
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
            display: none;
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
                    <a href="<?=Utility::url(__DIR__.'/../../index.php')?>" data-oaoaoa="invisible">Лабораторная работа №I</a><br />
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
                 --><span data-oaoaoa="ib" style="transform: rotate(4deg);">008</span>
                </h2>
            </td>
        </tr>

        <tr class="header">
            <td style="position: relative;">
                <?php $this->areaView->render(); ?>

                <h3 style="margin-right: -46px; transform: rotate(1deg); position: absolute; right: 0;" data-oaoaoa="ib align-right">
                    Доморацкого&nbsp;&nbsp;<br />
                    Эридана <span data-oaoaoa="ib drop">А</span>лексеевича&nbsp;<br />
                    Группа: P3<span style="font-size: 22pt; line-height: 32pt; vertical-align: middle;">2</span>11
                </h3>
            </td>
        </tr>

        <tr><td><?php $this->compView->render(); ?></td></tr>

        <tr>
            <td>
                <div class="fancy-box" data-oaoaoa="small-text">
                    <table>
                        <tr>
                            <td>Current time:</td>
                            <td data-oaoaoa="align-justify" id="current-time">
                                <?=date('Y-m-d H:i:s', $this->model->getCurrentTime())?>
                                <?=date_default_timezone_get()?>
                            </td>
                        </tr>
                        <tr>
                            <td>Elapsed time:</td>
                            <td data-oaoaoa="align-justify"><?=sprintf('%.15f', $this->model->getElapsedTime())?> sec.</td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>

        <tr><td><br />&nbsp;&nbsp;&nbsp;<a href="http://ifmo.ru/" target="_blank"><img src="<?=Utility::inlineImage(__DIR__.'/../images/itmo-logo.png')?>" alt="IT's More Than a University!" style="display: inline-block; transform: rotate(2deg);" /></a></td></tr>
    </table>

    <div id="jepa">
        <!--suppress CheckImageSize -->
        <img src="<?=Utility::url(__DIR__.'/../images/1-12676-512.png')?>"
             style="display: inline-block; background: url('<?=Utility::inlineImage(__DIR__.'/../images/1-12676-128.png')?>');"
             width="128" height="128" alt="" />
    </div>

    <div id="rocket"></div>
</div>

<?php if ($this->model->doFrontendTimeUpdate()): ?>
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
<?php endif; ?>
</body>
</html>
