const area = new (function () {
    "use strict";

    const availableR = [1, 1.25, 1.5, 1.75, 2, 2.25, 2.5, 2.75, 3, 3.25, 3.5, 3.75, 4];

    const canvasWidth = 400;
    const canvasHeight = 400;

    const self = this;
    this.onRChanged = function (r) {
        r = +r;

        if (isNaN(r) || !availableR.includes(r)) {
            return;
        }

        self.r = r;
        setTimeout(self.repaint, 1);
    };

    this.repaint = function () {
        const canvas = document.getElementById("areaCanvas");
        const context = canvas.getContext("2d");

        context.resetTransform();
        context.clearRect(0, 0, canvas.width, canvas.height);
        context.translate(canvasWidth / canvas.width, canvasHeight / canvas.height);

        context.fillStyle = "#F0F0F0";
        context.fillText(self.r, 34, 34);
    };
})();
