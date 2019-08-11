<?php

namespace Lab_Web\View\AreaView;

class CanvasRenderer extends Renderer {

    private $width, $height;

    public function __construct($width, $height) {
        $this->width = $width;
        $this->height = $height;
    }

    /**
     * Prints HTML code with canvas and script tags.
     *
     * @inheritDoc
     *
     * @param string $path path to area image
     * @param string $canvasId id of HTML canvas element
     *
     * @return bool always true
     */
    public function render($compModel, $path, $canvasId) {
        $this->recalc($this->width, $this->height, $compModel->getR());

        require __ROOT__.'/assets/templates/area/canvas.php';
    }
}
