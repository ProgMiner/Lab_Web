<?php /** @noinspection PhpUndefinedVariableInspection */
if (!defined('LAB1_WEB')) die();

use Lab_Web\Utility; ?>
<form action="<?=Utility::url(__DIR__.'/../../../index.php')?>" method="get" id="form" class="fancy-box">
    <table>
        <tr class="form-error-container">
            <td id="form-error-container" colspan="2"></td>
        </tr>
        <tr>
            <td>X: </td>
            <td>
                <?php foreach ([-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2] as $x): ?>
                    <label><input type="checkbox" name="x[]" value="<?=$x?>" <?=in_array($x, $xes) ? 'checked' : ''?>/><?=$x?> </label>
                <?php endforeach; ?><!--
         --></td>
        </tr>
        <tr>
            <td><label for="y-input">Y: </label></td>
            <td><input type="text" name="y" id="y-input"<?=is_numeric($y) ? " value=\"$y\"" : ''?> /></td>
        </tr>
        <tr>
            <td><label for="r-input">R: </label></td>
            <td><input type="text" name="r" id="r-input"<?=is_numeric($r) ? " value=\"$r\"" : ''?> /></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Поїхали!"></td>
        </tr>
    </table>
</form>

<script type="text/javascript">
    "use strict";

    function displayFormError(error, element) {
        document.querySelectorAll(".form-error-container").forEach(node => node.classList.add("shown-form-error-container"));
        document.getElementById("form-error-container").innerText = error;

        if (element !== undefined) {
            element.classList.add("bad-content");
        }
    }

    function hideFormError(element) {
        document.querySelectorAll(".form-error-container").forEach(node => node.classList.remove("shown-form-error-container"));

        if (element !== undefined) {
            element.classList.remove("bad-content");
        }
    }

    const xCheckboxes = document.querySelectorAll("#form input[type=checkbox][name='x[]']");

    function validateX(submit) {
        if (Array.prototype.slice.call(xCheckboxes).filter((cb) => cb.checked).length > 0) {
            return true;
        }

        if (submit) {
            displayFormError("No one X value checked!");
        } else {
            hideFormError();
        }

        return false;
    }

    xCheckboxes.forEach((node) => node.onclick = () => validateX(false));

    function validateField(value, submit, fieldElement, fieldName, min, max) {
        if (value.trim() === "") {
            if (submit) {
                displayFormError(fieldName + " cannot be empty!", fieldElement);
            } else {
                hideFormError(fieldElement);
            }

            return false;
        }

        const floatValue = parseFloat(value);

        if (floatValue.toString() !== value || isNaN(floatValue)) {
            displayFormError(fieldName + " is not a number!", fieldElement);
            return false;
        }

        if (floatValue < min || floatValue > max) {
            displayFormError(fieldName + " must be in [" + min + ", " + max + "]!", fieldElement);
            return false;
        }

        hideFormError(fieldElement);
        return true;
    }

    const yInput = document.getElementById("y-input");
    const rInput = document.getElementById("r-input");

    const validateY = (value, submit) => validateField(value, submit, yInput, "Y", -3, 5);
    const validateR = (value, submit) => validateField(value, submit, rInput, "R", 1, 4);

    yInput.oninput = () => validateY(yInput.value, false);
    rInput.oninput = () => validateR(rInput.value, false);

    document.getElementById("form").onsubmit = () =>
        validateX(true) && validateY(yInput.value, true) && validateR(rInput.value, true);
</script>
