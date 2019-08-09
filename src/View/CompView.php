<?php

namespace Lab_Web\View;

use Lab_Web\Model\CompModel;
use Lab_Web\View;

class CompView implements View {

    private $model;

    public function __construct($model) {
        if (!$model instanceof CompModel) {
            throw new \InvalidArgumentException(sprintf('$model is not %s!', CompModel::class));
        }

        $this->model = $model;
    }

    public function render() {
        $xes = $this->model->getXes();
        $y = $this->model->getY();
        $r = $this->model->getR();

        if ($this->model->isResultAvailable()) {
            sort($xes);

            foreach ($xes as $x) {
                /** @noinspection PhpUnusedLocalVariableInspection */
                $result = $this->model->getResult($x, $y, $r);

                require __DIR__ . '/../../assets/templates/comp/result.php';
            }
        } else {
            require __DIR__ . '/../../assets/templates/comp/form.php';
        }
    }
}
