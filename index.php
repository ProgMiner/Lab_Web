<?php

use Lab_Web\Factory;
use Lab_Web\View\CompView;
use Lab_Web\View\MainView;

require_once 'autoload.php';

define('LAB1_WEB', '');
define('__ROOT__', __DIR__);

(new MainView(Factory::makeMainModel(), new CompView(Factory::makeCompModel())))->render();
