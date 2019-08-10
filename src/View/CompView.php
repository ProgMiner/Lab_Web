<?php

namespace Lab_Web\View;

use Lab_Web\Model\CompModel;
use Lab_Web\Utility;
use Lab_Web\View;

class CompView implements View {

    private $model;

    public function __construct($model) {
        $this->model = Utility::assertInstanceOf($model, CompModel::class);
    }

    public function render() {
        $xes = $this->model->getXes();
        $y = $this->model->getY();
        $r = $this->model->getR();

        if ($this->model->isResultAvailable()) {
            sort($xes);

            echo '<!--';
            foreach ($xes as $x) {
                /** @noinspection PhpUnusedLocalVariableInspection */
                $result = $this->model->getResult($x, $y, $r);

                require __DIR__ . '/../../assets/templates/comp/result.php';
            }
            echo '-->';
        } else {
            require __DIR__ . '/../../assets/templates/comp/form.php';
        }
    }
}
