<?php

namespace Lab_Web\View;

use Lab_Web\Model\MainModel;
use Lab_Web\Utility;
use Lab_Web\View;

class MainView implements View {

    private $model;
    private $areaView;
    private $compView;

    public function __construct($model, $areaView, $compView) {
        $this->model = Utility::assertInstanceOf($model, MainModel::class);
        $this->areaView = Utility::assertInstanceOf($areaView, AreaView::class);
        $this->compView = Utility::assertInstanceOf($compView, CompView::class);
    }

    public function render() {
        require __ROOT__.'/assets/templates/main.php';
    }
}
