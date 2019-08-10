<?php

namespace Lab_Web;

final class Utility {

    /**
     * @throws \Exception always
     */
    public function __construct() {
        throw new \Exception("Operation is not implemented");
    }

    public static function assertInstanceOf($object, $class) {
        if (!is_a($object, $class)) {
            throw new \InvalidArgumentException(sprintf('$model is not %s!', $class));
        }

        return $object;
    }

    public static function toFloat($value) {
        $float = (float)$value;

        if ((string)$float != $value) {
            throw new \InvalidArgumentException('$value is not a numeric!');
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
        return self::removeRelativeComponentsFromPath(str_replace(__ROOT__, '.', $path));
    }

    // https://www.php.net/manual/ru/function.realpath.php#84012
    public static function removeRelativeComponentsFromPath($path, $absolute = false) {
        $path = str_replace(array('/', '\\'), DIRECTORY_SEPARATOR, $path);
        $parts = array_filter(explode(DIRECTORY_SEPARATOR, $path), 'strlen');
        $absolute = $absolute && strpos($path, DIRECTORY_SEPARATOR) === 0;

        $absolutes = array();
        foreach ($parts as $part) {
            if ('.' == $part) {
                continue;
            }

            if ('..' == $part) {
                array_pop($absolutes);
            } else {
                $absolutes[] = $part;
            }
        }

        return ($absolute ? DIRECTORY_SEPARATOR : '').implode(DIRECTORY_SEPARATOR, $absolutes);
    }

    public static function inlineImage($path, $mime = null, $headers = []) {
        $path = self::removeRelativeComponentsFromPath($path, true);

        if (!is_string($mime)) {
            $mime = mime_content_type($path);
        }

        array_unshift($headers, $mime);

        return 'data:'.implode(';', $headers).';base64,'.base64_encode(file_get_contents($path));
    }
}

if(!function_exists('mime_content_type')) {

    function mime_content_type($filename) {

        $mime_types = array(

            'txt' => 'text/plain',
            'htm' => 'text/html',
            'html' => 'text/html',
            'php' => 'text/html',
            'css' => 'text/css',
            'js' => 'application/javascript',
            'json' => 'application/json',
            'xml' => 'application/xml',
            'swf' => 'application/x-shockwave-flash',
            'flv' => 'video/x-flv',

            // images
            'png' => 'image/png',
            'jpe' => 'image/jpeg',
            'jpeg' => 'image/jpeg',
            'jpg' => 'image/jpeg',
            'gif' => 'image/gif',
            'bmp' => 'image/bmp',
            'ico' => 'image/vnd.microsoft.icon',
            'tiff' => 'image/tiff',
            'tif' => 'image/tiff',
            'svg' => 'image/svg+xml',
            'svgz' => 'image/svg+xml',

            // archives
            'zip' => 'application/zip',
            'rar' => 'application/x-rar-compressed',
            'exe' => 'application/x-msdownload',
            'msi' => 'application/x-msdownload',
            'cab' => 'application/vnd.ms-cab-compressed',

            // audio/video
            'mp3' => 'audio/mpeg',
            'qt' => 'video/quicktime',
            'mov' => 'video/quicktime',

            // adobe
            'pdf' => 'application/pdf',
            'psd' => 'image/vnd.adobe.photoshop',
            'ai' => 'application/postscript',
            'eps' => 'application/postscript',
            'ps' => 'application/postscript',

            // ms office
            'doc' => 'application/msword',
            'rtf' => 'application/rtf',
            'xls' => 'application/vnd.ms-excel',
            'ppt' => 'application/vnd.ms-powerpoint',

            // open office
            'odt' => 'application/vnd.oasis.opendocument.text',
            'ods' => 'application/vnd.oasis.opendocument.spreadsheet',
        );

        $ext = strtolower(array_pop(explode('.',$filename)));
        if (array_key_exists($ext, $mime_types)) {
            return $mime_types[$ext];
        }
        elseif (function_exists('finfo_open')) {
            $finfo = finfo_open(FILEINFO_MIME);
            $mimetype = finfo_file($finfo, $filename);
            finfo_close($finfo);
            return $mimetype;
        }
        else {
            return 'application/octet-stream';
        }
    }
}
