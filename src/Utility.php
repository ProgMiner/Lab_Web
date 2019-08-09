<?php

namespace Lab_Web;

use InvalidArgumentException;

final class Utility {

    /**
     * @throws \Exception always
     */
    public function __construct() {
        throw new \Exception("Operation is not implemented");
    }

    public static function toFloat($value) {
        $float = (float)$value;

        if ((string)$float != $value) {
            throw new InvalidArgumentException('$value is not a numeric!');
        }

        return $float;
    }

    public static function toBoolean($str, $default) {
        switch ($str) {
        case 'false':
        case 'off':
        case 'no':
            return false;

        case 'true':
        case 'yes':
        case 'on':
            return true;
        }

        return $default;
    }
}
