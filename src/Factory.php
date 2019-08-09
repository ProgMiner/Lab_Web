<?php

namespace Lab_Web;

use Lab_Web\Model\CompModelImpl;
use Lab_Web\Model\MainModelImpl;

final class Factory {

    /**
     * @throws \Exception always
     */
    public function __construct() {
        throw new \Exception("Operation is not implemented");
    }

    public static function makeCompModel($xes = null, $y = null, $r = null) {
        if (!is_array($xes)) {
            $newXes = null;

            if (key_exists('x', $_GET)) {
                $newXes = $_GET['x'];

                if (!is_array($newXes)) {
                    $newXes = [$newXes];
                }

                foreach ($newXes as &$x) {
                    try {
                        $x = Utility::toFloat($x);
                    } catch (\InvalidArgumentException $e) {}
                }
            }

            if (is_array($newXes)) {
                $xes = $newXes;
            }
        }

        if (!is_numeric($y)) {
            try {
                $newY = null;

                if (key_exists('y', $_GET)) {
                    $newY = Utility::toFloat($_GET['y']);
                }

                if (is_numeric($newY)) {
                    $y = $newY;
                }
            } catch (\InvalidArgumentException $e) {
                $y = null;
            }
        }

        if (!is_numeric($r)) {
            try {
                $newR = null;

                if (key_exists('r', $_GET)) {
                    $newR = Utility::toFloat($_GET['r']);
                }

                if (is_numeric($newR)) {
                    $r = $newR;
                }
            } catch (\InvalidArgumentException $e) {
                $r = null;
            }
        }

        return new CompModelImpl($xes, $y, $r);
    }

    public static function makeMainModel($doFrontendTimeUpdate = null, $startTime = null) {
        $model = new MainModelImpl($startTime);

        if (!is_bool($doFrontendTimeUpdate)) {
            $newDoFrontendTimeUpdate = null;

            if (key_exists('doFrontendTimeUpdate', $_COOKIE)) {
                $newDoFrontendTimeUpdate = $_COOKIE['doFrontendTimeUpdate'];
            }

            if (key_exists('doFrontendTimeUpdate', $_GET)) {
                $newDoFrontendTimeUpdate = $_GET['doFrontendTimeUpdate'];
            }

            $doFrontendTimeUpdate = Utility::toBoolean($newDoFrontendTimeUpdate, $doFrontendTimeUpdate);
        }

        if (is_bool($doFrontendTimeUpdate)) {
            $model->setDoFrontendTimeUpdate($doFrontendTimeUpdate);
        }

        return $model;
    }
}
