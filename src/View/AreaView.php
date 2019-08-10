<?php /** @noinspection DuplicatedCode */

namespace Lab_Web\View;

use Lab_Web\Model\CompModel;
use Lab_Web\Utility;
use Lab_Web\View;

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
        $path = __DIR__.'/../../assets/images/areas.png';

        $generated = false;
        if ($this->model->isResultAvailable()) {
            $generated = true;

            $newPath = $this->renderImage($path);
            if ($newPath === null) {
                $generated = false;
            } else {
                $path = Utility::inlineImage($newPath);
            }
        }

        if (!$generated) {
            $path = Utility::inlineImage($path);
        }

        ?><img src="<?=$path?>" alt="Area" style="pointer-events: none; user-select: none;" /><?php
    }

    private function renderImage($areaPath) {
        if (!file_exists($this->cacheDir)) {
            mkdir($this->cacheDir, 0777, true);
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

        if (class_exists(\Imagick::class) && $this->renderImageImagick($path, $areaPath)) {
            return $path;
        }

        if (function_exists('imagecreatefrompng') && $this->renderImageGd($path, $areaPath)) {
            return $path;
        }

        return null;
    }

    private function renderImageImagick($path, $areaPath) {
        try {
            $image = new \Imagick($areaPath);

            $width = $image->getImageWidth();
            $height = $image->getImageHeight();

            $centerX = round($width / 2) - 1;
            $centerY = round($height / 2) - 1;

            $r = $this->model->getR();
            $zoomX = 80 * $width / 205 / $r;
            $zoomY = 80 * $height / 205 / $r;
            $y = -$this->model->getY() * $zoomY;

            $canvas = new \ImagickDraw();
            $canvas->setFillColor(new \ImagickPixel('#ff0000'));
            foreach ($this->model->getXes() as $x) {
                $canvas->circle($centerX + $x * $zoomX, $centerY + $y, 1, 1);
            }

            $image->drawImage($canvas);
            $image->writeImage($path);
        } catch (\ImagickException $e) {
            return false;
        }

        return true;
    }

    private function renderImageGd($path, $areaPath) {
        $image = imagecreatefrompng($areaPath);

        if ($image === false) {
            return false;
        }

        try {
            $width = imagesx($image);
            $height = imagesy($image);
            if ($width === false || $height === false) {
                throw new \RuntimeException();
            }

            $centerX = round($width / 2) - 1;
            $centerY = round($height / 2) - 1;

            $r = $this->model->getR();
            $zoomX = 80 * $width / 205 / $r;
            $zoomY = 80 * $height / 205 / $r;

            $color = imagecolorallocate($image, 255, 0, 0);
            if ($color === false) {
                throw new \RuntimeException();
            }

            $y = -$this->model->getY() * $zoomY;
            foreach ($this->model->getXes() as $x) {
                if (imagefilledellipse($image, $centerX + $x * $zoomX, $centerY + $y, 2, 2, $color) === false) {
                    throw new \RuntimeException();
                }
            }

            if (imagepng($image, $path) === false) {
                throw new \RuntimeException();
            }
        } catch (\RuntimeException $e) {
            return false;
        } finally {
            imagedestroy($image);
        }

        return true;
    }
}
