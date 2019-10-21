(function() {
    "use strict";

    updateCurrentTime();
    function updateCurrentTime() {
        document.getElementById("currentTime").innerText = getCurrentTime();
        setTimeout(updateCurrentTime, 8 * 1000);
    }

    function getCurrentTime() {
        const date = new Date();

        const offset = -date.getTimezoneOffset() / 60;
        return date.getFullYear() + '-' + complete(date.getMonth() + 1) + '-' + complete(date.getDate()) + ' ' +
            complete(date.getHours()) + ':' + complete(date.getMinutes()) + ':' + complete(date.getSeconds()) + ' ' +
            'UTC' + (offset > 0 ? '+' : '') + (offset !== 0 ? offset : '');
    }

    function complete(src, length = 2, char = '0') {
        src = src + '';

        while (src.length < length) {
            src = char + src;
        }

        return src;
    }
})();
