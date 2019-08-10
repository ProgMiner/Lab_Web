<?php

use Lab_Web\Factory;
use Lab_Web\View\AreaView;
use Lab_Web\View\CompView;
use Lab_Web\View\MainView;

require_once 'autoload.php';

define('LAB1_WEB', '');
define('__ROOT__', __DIR__);

boot();

function boot() {
    $compModel = Factory::makeCompModel();

    (new MainView(Factory::makeMainModel(), new AreaView($compModel), new CompView($compModel)))->render();
}
