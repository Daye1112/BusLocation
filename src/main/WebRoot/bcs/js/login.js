window.onload = function() {
    //0.获取所需元素
    var $psw = $('#password');
    var $user = $('#workId');
    var $verifyCode = $('#verify_code');
    var $loginBtn = $('button');
    var $inputs = $('input');
    var $form = $('form');
    var $back = $('.back');
    var $forget = $('.forget');
    var $codeImg = $('.code-img');
    var flag = false;
    //1.监听焦点事件
    $inputs.focus(function() {
        $(this).parent('.line').addClass('focus');
        $(this).parent('.line').siblings('.line').removeClass('focus');
    });
    //2.阻止表单默认提交事件
    $form.submit(function() {
        return false;
    });
    //3.监听输入事件
    inputListener($inputs);
    //4.监听提交按钮点击事件
    submitListener($loginBtn, $user, $psw);
    //5.自动输入
    autoInput($user, $psw);
    //6.监听返回按钮点击事件
    $back.click(function() {
        history.go(-1);
    });
    //7.监听忘记密码按钮点击事件
    $forget.click(function() {
        location.href = './resetPsw.html';
    });
    // 点击刷新验证码
    $codeImg.click(function() {
        this.src = '/busposition/code/getVerifyCode?t=' + (new Date()).getTime();
        console.log(this.src);
    })

    // 监听提交按钮点击事件
    function submitListener($loginBtn, $user, $psw) {
        $loginBtn.click(function(event) {
            event.preventDefault();
            if (flag) {
                var workId = $user.val();
                var password = $psw.val();
                var verifyCode = $verifyCode.val();
                var obj = {};
                obj.workId = workId;
                obj.password = password;
                obj.verifyCode = verifyCode;
                obj.loginType = 'simple';
                $.ajax('/busposition/login', {
                    type: 'post',
                    data: JSON.stringify(obj),
                    dataType: 'json',
                    contentType: 'application/json',
                    success: function(data) {
                        console.log(data);
                        if (data.success === true) {
                            //保存账号，密码
                            $.addCookie({
                                key: 'workId',
                                value: workId,
                                path: '/',
                                day: 7
                            });
                            $.addCookie({
                                key: 'password',
                                value: password,
                                path: '/',
                                day: 7
                            });
                            //页面跳转
                            location.href = './realTimeRoute.html';
                        } else {
                            var promptWindow = new PromptWindow(data.message);
                            promptWindow.init();
                        }
                    },
                    error: function(xhr) {
                        var promptWindow = new PromptWindow('提交请求发送失败');
                        promptWindow.init();
                    }
                })
            }
        });
    }
    //监听表单输入事件
    function inputListener($inputs) {
        for (var i = 0; i < $inputs.length; i++) {
            var oInput = $inputs[i];
            oInput.oninput = debounce(function() {
                //如果用户名或密码为空
                if (!$psw.val().length || !$user.val().length) {
                    $loginBtn.css({ 'backgroundColor': '#99CCFF' });
                    flag = false;
                } else {
                    $loginBtn.css({ 'backgroundColor': '#51acfe' });
                    flag = true;
                }
            }, 300)
        }

    }
    //自动输入
    function autoInput($user, $psw) {
        var user = $.getCookie('workId', '/');
        var psw = $.getCookie('password', '/');
        if (user && psw) {
            $user.val(user);
            $psw.val(psw);
            $loginBtn.css({ 'backgroundColor': '#51acfe' });
            flag = true;
        }
    }

};