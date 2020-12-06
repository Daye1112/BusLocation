;(function($,window){
    $.extend({
        //添加cookie
        //key,value,day,path,domain
        addCookie: function(options){
            //设置cookie保存位置
            var index = location.pathname.lastIndexOf('/');
            var myPath = location.pathname.slice(0,index);
            options.path = options.path || myPath;
            //设置域名
            options.domain = options.domain || document.domain;
            //设置日期
            var date = new Date();
            date.setDate(date.getDate() + options.day);
            document.cookie = options.key+'='+options.value+';expires='+date.toGMTString()+';path='+options.path+';domain='+options.domain+';';
        },

        //获取cookie
        getCookie: function(key){
            var cookies = document.cookie.split(';');
            for(var i = 0; i < cookies.length; i++){
                var arr = cookies[i].split('=');
                if(arr[0].trim() === key){
                    return arr[1];
                }
            }
            return '';
        },
        //删除cookie
        //注意删除cookie要指定路径
        delCookie: function(key,path){

            this.addCookie({
                key: key,
                value: this.getCookie('key'),
                day: -1,
                path: '/'
            });
        },
    })
})(jQuery,window);