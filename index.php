<?php

use Lab_Web\Factory;
use Lab_Web\View\CompView;
use Lab_Web\View\MainView;

require_once 'vendor/autoload.php';

define('LAB1_WEB', '');

(new MainView(Factory::makeMainModel(), new CompView(Factory::makeCompModel())))->render();
