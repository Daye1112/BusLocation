(function(window){
    //函数防抖
    function debounce(fn,delay){
        var timer = null;
        // var arg = arguments;
        return function() {
            var self = this;
            var args = arguments;
            timer && clearTimeout(timer);
            timer = setTimeout(function () {
                fn.apply(self,args);
            }, delay || 1000)
        }
    }
    //函数节流
    function throttle(fn,delay){
        var timer = null;
        var flag = true;
        // var arg = arguments;
        return function() {
            var self = this;
            var args = arguments;
            if(!flag) return;
            flag = false;
            timer && clearTimeout(timer);
            timer = setTimeout(function () {
                flag = true;
                fn.apply(self,args);
            }, delay || 1000)
        }
    }
    //返回指定范围中的随机整数
    function getRandomIntInclusive(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }
    //判断是否是电脑端
    function IsPC() {
        var userAgentInfo = navigator.userAgent;
        var Agents = ["Android", "iPhone",
            "SymbianOS", "Windows Phone",
            "iPad", "iPod"];
        var flag = true;
        for (var v = 0; v < Agents.length; v++) {
            if (userAgentInfo.indexOf(Agents[v]) > 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    window.debounce = debounce;
    window.throttle = throttle;
    window.getRandomIntInclusive = getRandomIntInclusive;
    window.IsPC = IsPC;

})(window);