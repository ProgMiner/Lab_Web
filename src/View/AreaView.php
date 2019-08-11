<?php

namespace Lab_Web\View;

use Lab_Web\Model\CompModel;
use Lab_Web\Utility;
use Lab_Web\View;
use Lab_Web\View\AreaView\CanvasRenderer;
use Lab_Web\View\AreaView\GdRenderer;
use Lab_Web\View\AreaView\ImagickRenderer;
use Lab_Web\View\AreaView\Renderer;

class AreaView implements View {

    /**
     * @var CompModel
     */
    private $model;

    private $cacheDir = __ROOT__.'/area_cache';

    public function __construct($model) {
        $this->model = Utility::assertInstanceOf($model, CompModel::class);
    }

    public function render() {
        $path = __ROOT__.'/assets/images/areas.png';

        $canvas = false;
        if ($this->model->isResultAvailable()) {
            $newPath = $this->renderImage($path);

            if ($newPath === null || true) {
                $canvas = true;
            } else {
                /** @noinspection PhpUnusedLocalVariableInspection */
                $path = $newPath;
            }
        }

        if ($canvas) {
            (new CanvasRenderer(205, 205))->render($this->model, $path, 'area-canvas');
        } else {
            require __ROOT__.'/assets/templates/area/img.php';
        }
    }

    private function renderImage($areaPath) {
        if (!file_exists($this->cacheDir)) {
            if (!mkdir($this->cacheDir, 0777, true)) {
                return null;
            }
        }

        if (!is_dir($this->cacheDir)) {
            throw new \RuntimeException("Cache directory is not a directory");
        }

        $xes = $this->model->getXes();
        sort($xes);

        $path = $this->cacheDir.'/'.md5(implode($xes).$this->model->getY().$this->model->getR()).'.png';
        if (file_exists($path)) {
            return $path;
        }

        foreach ([ImagickRenderer::class, GdRenderer::class] as $rendererType) {
            /** @var Renderer $renderer */
            $renderer = new $rendererType();

            if ($renderer->render($this->model, $areaPath, $path)) {
                return $path;
            }
        }

        return null;
    }
}
