<?php

namespace Lab_Web\Model;

class MainModelImpl implements MainModel {

    private $doFrontendTimeUpdate = true;
    private $startTime;

    public function __construct($startTime = null) {
        if ($startTime == null) {
            $startTime = microtime(true);
        }

        $this->startTime = $startTime;
    }

    public function getCurrentTime() {
        return time();
    }

    public function getElapsedTime() {
        return microtime(true) - $this->startTime;
    }

    public function doFrontendTimeUpdate() {
        return $this->doFrontendTimeUpdate;
    }

    /**
     * @param bool $doFrontendTimeUpdate
     */
    public function setDoFrontendTimeUpdate($doFrontendTimeUpdate) {
        $this->doFrontendTimeUpdate = $doFrontendTimeUpdate;
    }
}
