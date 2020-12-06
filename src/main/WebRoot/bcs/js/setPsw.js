window.onload = function(){
    //0.获取所需元素
    var $back = $('.back');
    var $newPsw = $('.new-psw');
    var $confirmPsw = $('.confirm-psw');
    var $errorMes = $('.error-mes');
    var $setBtn = $('.main button');
    var $code = $('.code');
    //1.添加返回按钮点击事件
    $back.click(function(){
        history.go(-1);
    });
    //2.监听设置密码点击事件
    $setBtn.click(function(){
       if(judge($code,$newPsw,$confirmPsw)){
           sendSetPswRequest($code,$confirmPsw);
       }
    });
    //发送密码设置请求
    function sendSetPswRequest($code,$confirmPsw){
        var obj = {
            checkCode: $code.val(),
            password: $confirmPsw.val()
        };
        $.ajax('/busposition/user/modify/pwd/email',{
            type: 'post',
            data: obj,
            // contentType: 'application/json;charset=utf-8',
            success: function(data){
                var promptWindow = new PromptWindow(data.message);
                promptWindow.init();
                if(data.success === true){
                    setTimeout(function(){
                        location.href = './login.html';
                    },3000);
                }
            },
            error: function(){
                var promptWindow = new PromptWindow('密码设置请求失败');
                promptWindow.init();
            }
        })
    }
    //输入检验
    function judge($code,$newPsw,$confirmPsw){
        var codeVal = $code.val();
        var newVal = $newPsw.val();
        var confirmVal = $confirmPsw.val();
        var reg = /^\d{6}$/;
        if(!codeVal || !newVal || !confirmVal){
            $errorMes.text('表单含空项!');
            return false;
        }else if(!reg.test(codeVal)){
            console.log(codeVal);
            $errorMes.text('验证码格式错误!');
            return false;
        } else if(newVal !== confirmVal){
            console.log(newVal);
            console.log(confirmVal);
            $errorMes.text('两次密码输入不同!');
            return false;
        } else {
            $errorMes.text('');
            return true;
        }
    }
};