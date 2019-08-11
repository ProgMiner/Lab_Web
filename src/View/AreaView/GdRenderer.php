<?php

namespace Lab_Web\View\AreaView;

class GdRenderer extends Renderer {

    public function render($compModel, $areaPath, $path) {
        $image = imagecreatefrompng($areaPath);

        try {
            self::checkResult($image);
        } catch (GdException $e) {
            return false;
        }

        try {
            $this->recalc(
                self::checkResult(imagesx($image)),
                self::checkResult(imagesy($image)),
                $compModel->getR()
            );

            $color = self::checkResult(imagecolorallocate($image, 255, 0, 0));

            $y = $this->translateY($compModel->getY());
            foreach ($compModel->getXes() as $x) {
                imagefilledellipse($image, $this->translateX($x), $y, 3, 3, $color);
            }

            self::checkResult(imagepng($image, $path));
        } catch (GdException $e) {
            return false;
        } finally {
            imagedestroy($image);
        }

        return true;
    }

    public static function canBeUsed() {
        return function_exists('imagecreatefrompng');
    }

    /**
     * @param $result mixed value from GD function
     *
     * @return mixed specified result
     *
     * @throws GdException if value is FALSE
     */
    private static function checkResult($result) {
        if ($result === false) {
            throw new GdException();
        }

        return $result;
    }
}
