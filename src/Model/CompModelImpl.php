<?php

namespace Lab_Web\Model;

class CompModelImpl implements CompModel {

    private $available = true;

    private $xes, $y, $r;

    public function __construct($xes = null, $y = null, $r = null) {
        if (!is_array($xes)) {
            $xes = [];

            $this->available = false;
        } else {
            $newXes = [];

            foreach ($xes as $x) {
                switch ($x) {
                case -2:
                case -1.5:
                case -1:
                case -0.5:
                case 0:
                case 0.5:
                case 1:
                case 1.5:
                case 2:
                    array_push($newXes, $x);
                }
            }

            $xes = $newXes;
            if (empty($xes)) {
                $this->available = false;
            }
        }

        if (!is_numeric($y)) {
            $y = null;

            $this->available = false;
        } else if ($y < -3 || $y > 5) {
            $this->available = false;
        }

        if (!is_numeric($r)) {
            $r = null;

            $this->available = false;
        } else if ($r < 1 || $r > 4) {
            $this->available = false;
        }

        $this->xes = $xes;
        $this->y = $y;
        $this->r = $r;
    }

    public function isResultAvailable() {
        return $this->available;
    }

    public function getXes() {
        return $this->xes;
    }

    public function getY() {
        return $this->y;
    }

    public function getR() {
        return $this->r;
    }

    public function getResult($x, $y, $r) {
        if ($x >= 0 && $y <= 0 && $x * $x + $y * $y < $r * $r) {
            return true;
        }

        if ($x <= 0 && $y <= 0 && $x >= -$r && $y > -$r / 2) {
            return true;
        }

        if ($x <= 0 && $y >= 0 && 2 * $y < $x + $r) {
            return true;
        }

        return false;
    }
}
