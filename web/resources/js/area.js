const area = new (function () {
    "use strict";

    const availableR = [1, 1.25, 1.5, 1.75, 2, 2.25, 2.5, 2.75, 3, 3.25, 3.5, 3.75, 4];

    const canvasWidth = 400;
    const canvasHeight = 400;
    const canvasStep = canvasHeight / 2 / 12;

    const canvasColorPrimary = '#090909';
    const canvasColorSecondary = '#C0C0C0';
    const canvasColorBackground = '#F9F9F9';

    const self = this;

    this.r = null;
    this.history = [];
    this.onRChanged = function (r) {
        r = +r;

        if (isNaN(r) || !availableR.includes(r)) {
            this.r = null;
        } else {
            self.r = r;
        }

        setTimeout(self.repaint, 1);
    };

    this.setHistory = function(history) {
        if (history instanceof Array) {
            this.history = history;
            self.repaint();
        }
    };

    this.repaint = function () {
        const canvas = document.getElementById('areaCanvas');
        const context = canvas.getContext('2d');

        const actualCanvasSize = {
            width: parseInt(getCurrentStyle(canvas, 'width'), 10),
            height: parseInt(getCurrentStyle(canvas, 'height'), 10)
        };

        context.resetTransform();
        const scale = Math.min(
            actualCanvasSize.width / canvasWidth,
            actualCanvasSize.height / canvasHeight
        );

        context.scale(scale, scale);
        context.translate(
            (actualCanvasSize.width / scale - canvasWidth) / 2,
            (actualCanvasSize.height / scale - canvasHeight) / 2
        );

        context.globalCompositeOperation = 'source-over';
        context.clearRect(0, 0, canvasWidth, canvasHeight);

        const R = self.r == null ? 'R' : self.r;
        const halfR = self.r == null ? 'R/2' : (self.r / 2);

        context.strokeStyle = canvasColorPrimary;
        context.fillStyle = canvasColorBackground;
        context.font = `bold ${canvasStep - 2}px 'Courier New', monospace`;
        context.fillRect(0, 0, canvasWidth, canvasHeight);

        // Grid
        context.strokeStyle = canvasColorSecondary;

        context.beginPath();
        for (let i = 1; i < 24; ++i) {
            context.moveTo(canvasStep / 4, i * canvasStep);
            context.lineTo(canvasWidth - canvasStep / 4, i * canvasStep);

            context.moveTo(i * canvasStep,canvasStep / 4);
            context.lineTo(i * canvasStep, canvasHeight - canvasStep / 4);
        }
        context.stroke();

        context.strokeStyle = canvasColorPrimary;
        context.fillStyle = canvasColorBackground;

        // Circles
        context.beginPath();
        context.fillStyle = canvasColorPrimary;
        context.arc(canvasWidth / 2, canvasHeight / 2, canvasStep * 5, 0, 2 * Math.PI);
        context.fill();

        context.fillStyle = canvasColorBackground;

        const drawCircle = (i) => {
            context.beginPath();
            context.arc(canvasWidth / 2, canvasHeight / 2, canvasStep * i, 0, 2 * Math.PI);
            context.stroke();
        };

        context.strokeStyle = canvasColorBackground;
        [0.5, 1, 2, 3, 4].forEach(drawCircle);

        context.strokeStyle = canvasColorPrimary;
        [6, 7, 8, 9, 10].forEach(drawCircle);

        // Axises
        context.beginPath();
        context.lineWidth = 2;
        context.strokeStyle = canvasColorBackground;
        context.globalCompositeOperation = 'difference';
        context.moveTo(canvasStep / 2, canvasHeight / 2);
        context.lineTo(canvasWidth - canvasStep / 2, canvasHeight / 2);
        context.lineTo(canvasWidth - canvasStep, canvasHeight / 2 - canvasStep / 2);
        context.moveTo(canvasWidth - canvasStep, canvasHeight / 2 + canvasStep / 2);
        context.lineTo(canvasWidth - canvasStep / 2, canvasHeight / 2);

        context.moveTo(canvasWidth / 2, canvasHeight - canvasStep / 2);
        context.lineTo(canvasWidth / 2, canvasStep / 2);
        context.lineTo(canvasWidth / 2 - canvasStep / 2, canvasStep);
        context.moveTo(canvasWidth / 2 + canvasStep / 2, canvasStep);
        context.lineTo(canvasWidth / 2, canvasStep / 2);
        context.stroke();

        context.lineWidth = 1;
        context.strokeStyle = canvasColorPrimary;
        context.globalCompositeOperation = 'source-over';

        // Pictogramms
        const pictogrammsLabelText1 = ` < ${R}`;
        const pictogrammsLabelText2 = ` < ${halfR}`;

        const pictogrammsLabelsWidth = Math.max(
            context.measureText(pictogrammsLabelText1).width,
            context.measureText(pictogrammsLabelText2).width,
        );

        const pictogrammsRightSide = Math.max(
            (Math.ceil((canvasStep * 1.5 + pictogrammsLabelsWidth) / canvasStep) + 0.5) * canvasStep,
            canvasStep * 5.5
        );

        context.beginPath();
        context.moveTo(canvasStep / 2, canvasStep * 0.75);
        context.lineTo(pictogrammsRightSide, canvasStep * 0.75);
        context.lineTo(pictogrammsRightSide, canvasStep * 4.25);
        context.lineTo(canvasStep / 2, canvasStep * 4.25);
        context.lineTo(canvasStep / 2, canvasStep * 0.75);
        context.fill();
        context.stroke();

        context.beginPath();
        context.moveTo(canvasStep, canvasStep * 1.25);
        context.lineTo(canvasStep * 2, canvasStep * 1.25);
        context.lineTo(canvasStep * 2, canvasStep * 2.25);
        context.lineTo(canvasStep, canvasStep * 2.25);
        context.lineTo(canvasStep, canvasStep * 1.25);
        context.fill();
        context.stroke();

        context.beginPath();
        context.fillStyle = canvasColorPrimary;
        context.moveTo(canvasStep, canvasStep * 2.75);
        context.lineTo(canvasStep * 2, canvasStep * 2.75);
        context.lineTo(canvasStep * 2, canvasStep * 3.75);
        context.lineTo(canvasStep, canvasStep * 3.75);
        context.lineTo(canvasStep, canvasStep * 2.75);
        context.fill();
        context.stroke();

        context.fillText(pictogrammsLabelText1, canvasStep * 2, whereMeDrawText(context, canvasStep * 1.25));
        context.fillText(pictogrammsLabelText2, canvasStep * 2, whereMeDrawText(context, canvasStep * 2.75));
        context.fillStyle = canvasColorBackground;

        // Batman
        context.globalCompositeOperation = 'difference';
        context.drawImage(document.getElementById('batmanImage'), 0, 0, canvasWidth, canvasHeight);
        context.globalCompositeOperation = 'source-over';

        // History

        const bulletHoleGreen = document.getElementById('bulletHoleGreenImage');
        const bulletHoleRed = document.getElementById('bulletHoleRedImage');

        const centerX = canvasWidth / 2;
        const centerY = canvasHeight / 2;
        const zoomX = canvasWidth * 10 / 24 / self.r;
        const zoomY = canvasHeight * 10 / 24 / self.r;
        for (const pointKey in self.history) {
            if (!self.history.hasOwnProperty(pointKey)) {
                continue;
            }

            const point = self.history[pointKey];
            if (self.r != null && point.r !== self.r) {
                continue;
            }

            let actualZoomX = zoomX, actualZoomY = zoomY;
            if (self.r == null) {
                actualZoomX = canvasWidth * 10 / 24 / point.r;
                actualZoomY = canvasHeight * 10 / 24 / point.r;
            }

            context.drawImage(
                point.result ? bulletHoleGreen : bulletHoleRed,
                centerX + point.x * actualZoomY - 15,
                centerY - point.y * actualZoomY - 15,
                30, 30
            );
        }

        // Clip
        context.beginPath();
        context.rect(0, 0, canvasWidth, canvasHeight);
        context.clip();
    };

    function getCurrentStyle(element, style) {
        try {
            return window.getComputedStyle(element, null).getPropertyValue(style);
        } catch (e) {
            // noinspection JSUnresolvedVariable
            return element.currentStyle[style];
        }
    }

    function whereMeDrawText(context, topY, height = canvasStep) {
        return (height + context.measureText('M').width) / 2 + topY
    }
})();
