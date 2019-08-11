<?php

namespace Lab_Web\View\AreaView;

use Lab_Web\Model\CompModel;

abstract class Renderer {

    private $centerX = 0, $centerY = 0;
    private $zoomX = 0, $zoomY = 0;

    /**
     * @param $compModel CompModel model for render
     * @param $areaPath string path to area image
     * @param $path string path to result
     *
     * @return boolean is rendered successful
     */
    public abstract function render($compModel, $areaPath, $path);

    protected function recalc($width, $height, $r) {
        $this->centerX = round($width / 2) - 1;
        $this->centerY = round($height / 2) - 1;

        $this->zoomX = 80 * $width / 205 / $r;
        $this->zoomY = 80 * $height / 205 / $r;
    }

    protected function translateX($x) {
        return $this->centerX + $x * $this->zoomX;
    }

    protected function translateY($y) {
        return $this->centerY - $y * $this->zoomY;
    }
}
