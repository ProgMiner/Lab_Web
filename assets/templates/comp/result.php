<?php /** @noinspection PhpUndefinedVariableInspection */
if (!defined('LAB1_WEB')) die(); ?>
<table class="fancy-box">
    <tr>
        <td>X: </td>
        <td><?=$x?></td>
    </tr>
    <tr>
        <td>Y: </td>
        <td><?=$y?></td>
    </tr>
    <tr>
        <td>R: </td>
        <td><?=$r?></td>
    </tr>
    <tr>
        <td>Result: </td>
        <td data-oaoaoa="align-justify">point is<?=$result ? '' : "n't"?> included in the area</td>
    </tr>
</table>
