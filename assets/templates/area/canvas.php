<?php /** @noinspection PhpUndefinedVariableInspection */
if (!defined('LAB1_WEB')) die();

use Lab_Web\Utility; ?>
<canvas id="<?=$canvasId?>" class="area" width="205" height="205" style="background: url('<?=Utility::inlineImage($path)?>');"><?php require 'img.php'; ?></canvas>

<script type="text/javascript">
    (function() {
        const canvas = document.getElementById("<?=$canvasId?>");
        const context = canvas.getContext("2d");

        context.fillStyle = "#ff0000";
        <?php $y = $this->translateY($compModel->getY());
        foreach ($compModel->getXes() as $x): ?>

        context.beginPath();
        context.arc(<?=$this->translateX($x) + 0.5?>, <?=$y + 0.5?>, 2, 0, 360);
        context.fill();
        <?php endforeach; ?>

    })();
</script>
