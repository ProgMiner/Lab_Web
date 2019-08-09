<?php

namespace Lab_Web\Model;

interface MainModel {

    public function getCurrentTime();
    public function getElapsedTime();
    public function doFrontendTimeUpdate();
}
