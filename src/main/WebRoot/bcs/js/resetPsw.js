window.onload = function(){
    //0.获取所需元素
    var $back = $('.back');
    var $mail = $('.main input');
    var $div = $('.main div');
    var $resetBtn = $('.main button');
    var $errorMes = $('.error-mes');
    var flag = true;
    //1.添加返回按钮点击事件
    $back.click(function(){
        history.go(-1);
    });
    //2.监听重置密码按钮点击事件
    $resetBtn.click(function(){
        if(judge($mail,$errorMes) && flag){
            sendResetRequest($mail.val());
            //设置延时发送
            setDelay($resetBtn)
        }
    });
    //3.设置延迟发送
    function setDelay($resetBtn){
        flag = false;
        var count = 30;
        var timer = setInterval(function(){
            $resetBtn.text(count+'s后可再次发送');
            count--;
            if(count === -1){
                flag = true;
                $resetBtn.text('重置密码');
                clearInterval(timer);
            }
        },1000);
        return timer;
    }
    //发送ajax
    function sendResetRequest(val){
        $.ajax('/busposition/user/send/email',{
            type: 'post',
            data: {email:val},
            success: function(data){
                if(data.success === true){
                    setTimeout(function(){
                        location.href = './setPsw.html';
                    },1000);
                }
                var promptWindow = new PromptWindow(data.message);
                promptWindow.init();
            },
            error: function(){
                var promptWindow = new PromptWindow('邮箱信息发送失败');
                promptWindow.init();
            }
        })
    }
    //输入验证
    function judge($mail,$errorMes){
        var val = $mail.val();
        var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        if(val === ''){
            setErrorStyle($mail,$errorMes,'邮箱输入为空!');
            return false;
        }else if(!reg.test(val)){
            setErrorStyle($mail,$errorMes,'邮箱格式错误!');
            return false;
        } else {
            $errorMes.text('');
            return true;
        }
    }
    //设置错误样式
    function setErrorStyle($mail,$errorMes,mes){
        $mail.val('');
        $mail.focus();
        $errorMes.text(mes);
    }


};
