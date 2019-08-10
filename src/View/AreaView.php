<?php

namespace Lab_Web\View;

use Lab_Web\Model\CompModel;
use Lab_Web\Utility;
use Lab_Web\View;

class AreaView implements View {

    private $model;

    public function __construct($model) {
        $this->model = Utility::assertInstanceOf($model, CompModel::class);
    }

    public function render() {
        // TODO paint points on the area

        if (!$this->model->isResultAvailable() || true) {
            ?><img src="<?=Utility::inlineImage(__DIR__.'/../../assets/images/areas.png')?>" alt="Area" style="pointer-events: none; user-select: none;" /><?php
        }
    }
}