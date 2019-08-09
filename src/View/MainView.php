<?php

namespace Lab_Web\View;

use Lab_Web\Model\MainModel;
use Lab_Web\View;

class MainView implements View {

    private $model;
    private $compView;

    public function __construct($model, $compView) {
        if (!$model instanceof MainModel) {
            throw new \InvalidArgumentException(sprintf('$model is not %s!', MainModel::class));
        }

        if (!$compView instanceof CompView) {
            throw new \InvalidArgumentException(sprintf('$compView is not %s!', CompView::class));
        }

        $this->model = $model;
        $this->compView = $compView;
    }

    public function render() {
        require __DIR__.'/../../assets/templates/main.php';
    }
}
