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

    public static function url($path) {
        return self::getAbsolutePath(str_replace(__ROOT__, '.', $path));
    }

    // https://www.php.net/manual/ru/function.realpath.php#84012
    public static function getAbsolutePath($path) {
        $path = str_replace(array('/', '\\'), DIRECTORY_SEPARATOR, $path);
        $parts = array_filter(explode(DIRECTORY_SEPARATOR, $path), 'strlen');
        $absolutes = array();
        foreach ($parts as $part) {
            if ('.' == $part) continue;
            if ('..' == $part) {
                array_pop($absolutes);
            } else {
                $absolutes[] = $part;
            }
        }
        return implode(DIRECTORY_SEPARATOR, $absolutes);
    }
}
