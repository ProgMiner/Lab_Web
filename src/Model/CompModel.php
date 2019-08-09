<?php

namespace Lab_Web\Model;

interface CompModel {

    public function isResultAvailable();

    public function getXes();
    public function getY();
    public function getR();

    public function getResult($x, $y, $r);
}
