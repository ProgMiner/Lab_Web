<?php

namespace Lab_Web\View\AreaView;

class ImagickRenderer extends Renderer {

    public function render($compModel, $areaPath, $path) {
        if (!self::canBeUsed()) {
            return false;
        }

        try {
            $image = new \Imagick($areaPath);

            $this->recalc($image->getImageWidth(), $image->getImageHeight(), $compModel->getR());

            $canvas = new \ImagickDraw();
            $canvas->setFillColor(new \ImagickPixel('#ff0000'));

            $y = $this->translateY($compModel->getY());
            foreach ($compModel->getXes() as $x) {
                $canvas->circle($this->translateX($x), $y, 3, 3);
            }

            $image->drawImage($canvas);
            $image->writeImage($path);
        } catch (\ImagickException $e) {
            return false;
        }

        return true;
    }

    public static function canBeUsed() {
        return class_exists(\Imagick::class);
    }
}
