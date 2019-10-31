const area = new (function () {
    "use strict";

    const availableR = [1, 1.25, 1.5, 1.75, 2, 2.25, 2.5, 2.75, 3, 3.25, 3.5, 3.75, 4];

    const canvasWidth = 400;
    const canvasHeight = 400;
    const canvasStepX = canvasWidth / 2 / 12;
    const canvasStepY = canvasHeight / 2 / 12;

    const canvasColorPrimary = '#090909';
    const canvasColorSecondary = '#C0C0C0';
    const canvasColorBackground = '#F9F9F9';

    const self = this;

    self.r = null;
    self.history = [];
    self.onRChanged = function (r) {
        r = +r;

        if (isNaN(r) || !availableR.includes(r)) {
            self.r = null;
        } else {
            self.r = r;
        }

        setTimeout(self.repaint, 1);
    };

    self.setHistory = function(history) {
        if (history instanceof Array) {
            self.history = history;
            self.repaint();
        }
    };

    self.onClickOnCanvas = function(canvas, canvasScale, canvasTranslate, r) {
        if (r == null) {
            return () => { errorPage('Вы не выбрали R.'); };
        }

        return function(event) {
            const offsetLeft = parseInt(getCurrentStyle(canvas, 'border-left-width'), 10);
            const offsetTop = parseInt(getCurrentStyle(canvas, 'border-top-width'), 10);

            const rect = event.target.getBoundingClientRect();
            const x = Math.ceil(event.clientX - rect.left - offsetLeft) / canvasScale - canvasTranslate.x;
            const y = (event.clientY - rect.top - offsetTop) / canvasScale - canvasTranslate.y;

            if (x < 0 || x >= canvasWidth || y < 0 || y >= canvasHeight) {
                return;
            }

            const centerX = canvasWidth / 2;
            const centerY = canvasHeight / 2;

            const zoomX = canvasWidth * 10 / 24 / r;
            const zoomY = canvasHeight * 10 / 24 / r;

            sendForm((x - centerX) / zoomX, (centerY - y) / zoomY, r);

            function sendForm(x, y, r) {
                document.getElementById('areaXField').value = x;
                document.getElementById('areaYField').value = y;
                document.getElementById('areaRField').value = r;

                document.getElementById('areaFormButton').click();
            }
        }
    };

    self.repaint = function () {
        const canvas = document.getElementById('areaCanvas');
        const context = canvas.getContext('2d');

        const actualCanvasSize = {
            width: parseInt(getCurrentStyle(canvas, 'width'), 10),
            height: parseInt(getCurrentStyle(canvas, 'height'), 10)
        };

        // Init canvas
        context.resetTransform();
        context.clearRect(0, 0, canvas.width, canvas.height);

        const canvasScale = Math.min(
            actualCanvasSize.width / canvasWidth,
            actualCanvasSize.height / canvasHeight
        );

        context.scale(canvasScale, canvasScale);

        const canvasTranslate = {
            x: (actualCanvasSize.width / canvasScale - canvasWidth) / 2,
            y: (actualCanvasSize.height / canvasScale - canvasHeight) / 2
        };

        context.translate(canvasTranslate.x, canvasTranslate.y);
        context.globalCompositeOperation = 'source-over';

        const R = self.r == null ? 'R' : self.r;
        const halfR = self.r == null ? 'R/2' : (self.r / 2);

        context.strokeStyle = canvasColorPrimary;
        context.fillStyle = canvasColorBackground;
        context.font = `bold ${canvasStepX - 2}px 'Courier New', monospace`;
        context.fillRect(0, 0, canvasWidth, canvasHeight);

        // Clip
        context.beginPath();
        context.rect(0, 0, canvasWidth, canvasHeight);
        context.clip();

        // Grid
        context.strokeStyle = canvasColorSecondary;

        context.beginPath();
        for (let i = 1; i < 24; ++i) {
            context.moveTo(canvasStepX / 4, i * canvasStepY);
            context.lineTo(canvasWidth - canvasStepX / 4, i * canvasStepY);

            context.moveTo(i * canvasStepX,canvasStepY / 4);
            context.lineTo(i * canvasStepX, canvasHeight - canvasStepY / 4);
        }
        context.stroke();

        context.strokeStyle = canvasColorPrimary;
        context.fillStyle = canvasColorBackground;

        // Circles
        context.beginPath();
        context.globalCompositeOperation = 'difference';
        context.ellipse(canvasWidth / 2, canvasHeight / 2, canvasStepX * 5, canvasStepY * 5, 0, 0, 2 * Math.PI);
        context.fill();

        context.globalCompositeOperation = 'source-over';

        const drawCircle = (i) => {
            context.beginPath();
            context.ellipse(canvasWidth / 2, canvasHeight / 2, canvasStepX * i, canvasStepY * i, 0, 0, 2 * Math.PI);
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
        context.moveTo(canvasStepX / 2, canvasHeight / 2);
        context.lineTo(canvasWidth - canvasStepX / 2, canvasHeight / 2);
        context.lineTo(canvasWidth - canvasStepX, canvasHeight / 2 - canvasStepY / 2);
        context.moveTo(canvasWidth - canvasStepX, canvasHeight / 2 + canvasStepY / 2);
        context.lineTo(canvasWidth - canvasStepX / 2, canvasHeight / 2);

        context.moveTo(canvasWidth / 2, canvasHeight - canvasStepY / 2);
        context.lineTo(canvasWidth / 2, canvasStepX / 2);
        context.lineTo(canvasWidth / 2 - canvasStepX / 2, canvasStepY);
        context.moveTo(canvasWidth / 2 + canvasStepX / 2, canvasStepY);
        context.lineTo(canvasWidth / 2, canvasStepY / 2);
        context.stroke();

        context.lineWidth = 1;
        context.strokeStyle = canvasColorPrimary;
        context.globalCompositeOperation = 'source-over';

        // Pictogramms
        const pictogrammsLabelText1 = ` < ${R}`;
        const pictogrammsLabelText2 = ` < ${halfR}`;

        const pictogrammsLabelsWidth = Math.max(
            context.measureText(pictogrammsLabelText1).width,
            context.measureText(pictogrammsLabelText2).width
        );

        const pictogrammsRightSide = Math.max(
            (Math.ceil((canvasStepX * 1.5 + pictogrammsLabelsWidth) / canvasStepX) + 0.5) * canvasStepX,
            canvasStepX * 5.5
        );

        context.beginPath();
        context.moveTo(canvasStepX / 2, canvasStepY * 0.75);
        context.lineTo(pictogrammsRightSide, canvasStepY * 0.75);
        context.lineTo(pictogrammsRightSide, canvasStepY * 4.25);
        context.lineTo(canvasStepX / 2, canvasStepY * 4.25);
        context.lineTo(canvasStepX / 2, canvasStepY * 0.75);
        context.fill();
        context.stroke();

        context.beginPath();
        context.moveTo(canvasStepX, canvasStepY * 1.25);
        context.lineTo(canvasStepX * 2, canvasStepY * 1.25);
        context.lineTo(canvasStepX * 2, canvasStepY * 2.25);
        context.lineTo(canvasStepX, canvasStepY * 2.25);
        context.lineTo(canvasStepX, canvasStepY * 1.25);
        context.fill();
        context.stroke();

        context.beginPath();
        context.fillStyle = canvasColorPrimary;
        context.moveTo(canvasStepX, canvasStepY * 2.75);
        context.lineTo(canvasStepX * 2, canvasStepY * 2.75);
        context.lineTo(canvasStepX * 2, canvasStepY * 3.75);
        context.lineTo(canvasStepX, canvasStepY * 3.75);
        context.lineTo(canvasStepX, canvasStepY * 2.75);
        context.fill();
        context.stroke();

        context.fillText(pictogrammsLabelText1, canvasStepX * 2, whereMeDrawText(context, canvasStepY * 1.25));
        context.fillText(pictogrammsLabelText2, canvasStepX * 2, whereMeDrawText(context, canvasStepY * 2.75));
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
                centerX + point.x * actualZoomX - 5,
                centerY - point.y * actualZoomY - 5,
                10, 10
            );
        }

        canvas.onclick = self.onClickOnCanvas(canvas, canvasScale, canvasTranslate, self.r);
    };

    function getCurrentStyle(element, style) {
        try {
            return window.getComputedStyle(element, null).getPropertyValue(style);
        } catch (e) {
            // noinspection JSUnresolvedVariable
            return element.currentStyle[style];
        }
    }

    function whereMeDrawText(context, topY, height = canvasStepY) {
        return (height + context.measureText('M').width) / 2 + topY
    }
})();
