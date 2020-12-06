window.onload = function () {
    //0.获取所需元素
    var $bodyIn = $('.body-in');
    var $departmentName = $('.department-name');
    var $realName = $('.real-name');
    var $phone = $('.phone');
    var $idCode = $('.id-code');
    var $workId = $('.work-id');
    var $email = $('.email');
    var $infoItems = $('.info-list li').not('.department-name').not('.work-id');
    var $modifyPsw = $('#modify-psw');
    var $exit = $('#exit');
    var $back = $('.back');
    var flag = true;


    //1.初始化用户信息
    initUserInfo();
    //2.监听用户信息列表点击事件
    $infoItems.click(function () {
        //初始化模态窗
        var modalWindow = new ModalWindow($bodyIn);
        modalWindow.init();
        //初始化模态窗标题
        var text = $(this).children('h3').eq(0).text();
        modalWindow.setTitle(text);
        //初始化模态窗内容
        var $modalInput = $(`<input type="text" placeholder="请输入要修改的${text}">`);
        modalWindow.$modalContent.append($modalInput);
        //初始化确定按钮点击事件
        var key = toHumpString(this.className);
        initBtnOk(modalWindow, $modalInput, key);
        //打开模态窗
        modalWindow.openModalWindow();
    });
    //3.监听密码修改按钮点击事件
    $modifyPsw.click(function () {
        //初始化模态窗
        var modalWindow = new ModalWindow($bodyIn);
        modalWindow.init();
        //初始化模态窗标题
        var text = $(this).text();
        modalWindow.setTitle(text);
        //初始化模态窗内容
        var $oldPsw = $(`<input type="text" placeholder="请输入原密码">`);
        var $newPsw = $(`<input type="text" placeholder="请输入新密码">`);
        modalWindow.$modalContent.append($oldPsw);
        modalWindow.$modalContent.append($newPsw);
        //打开模态窗
        modalWindow.openModalWindow();
        //监听密码框输入事件
        pswIsEmpty($oldPsw, $newPsw);
        //初始化模态窗确定事件
        modalWindow.$btnOk.click(function () {
            if (flag) {
                var oldVal = $oldPsw.val();
                var newVal = $newPsw.val();
                var obj = {
                    oldPassword: oldVal,
                    newPassword: newVal
                };
                //发送密码修改请求
                sendPswModify(obj);
                modalWindow.closeModalWindow(modalWindow.bomb());
            }
        })

    });
    //4.监听退出登录按钮点击事件
    $exit.click(function(){
       $.delCookie('password','/');
       $.delCookie('workId','/');
       location.href = './login.html';
    });
    //5.设置列表按下抬起样式
    setDownUpStyle($infoItems.add($modifyPsw).add($exit));
    //6.监听返回按钮点击事件
    $back.click(function(){
       history.go(-1);
    });


    //初始化确认按钮点击事件
    function initBtnOk(modalWindow, $modalInput, key) {
        modalWindow.$btnOk.click(function () {
            var value = $modalInput.val();
            //输入检测
            var flag = checkInput(key, value, $modalInput);
            if (!flag) return;
            //发送请求
            var data = {};
            data[key] = value;
            sendModify(data);
            //关闭并删除模态窗
            modalWindow.closeModalWindow(modalWindow.bomb());
        });
    }

    //填充用户数据
    function fillData(userInfo) {
        $departmentName.children('p').text(userInfo.departmentName);
        $realName.children('p').text(userInfo.realName);
        $phone.children('p').text(userInfo.phone);
        $idCode.children('p').text(userInfo.idCode);
        $workId.children('p').text(userInfo.workId);
        $email.children('p').text(userInfo.email);
    };

    //初始化用户信息
    function initUserInfo() {
        $.ajax('/busposition/user/self/info', {
            type: 'get',
            success: function (data) {
                if (data.success === true) {
                    var userInfo = data.data[0];
                    //2.填充用户数据
                    fillData(userInfo);
                } else {
                    var promptWindow = new PromptWindow(data.message);
                    promptWindow.init();
                }
            },
            error: function () {
                var promptWindow = new PromptWindow('用户信息请求发送失败');
                promptWindow.init();
            }
        });
    }

    //发送信息修改请求
    function sendModify(obj) {
        $.ajax('/busposition/user/update/info', {
            type: 'post',
            data: JSON.stringify(obj),
            contentType: 'application/json',
            success: function (data) {
                if (data.success === true) {
                    var promptWindow = new PromptWindow(data.message);
                    promptWindow.init();
                    //重新加载账号资料页面
                    initUserInfo();
                } else {
                    var promptWindow = new PromptWindow(data.message);
                    promptWindow.init();
                }
            },
            error: function (xhr) {
                var promptWindow = new PromptWindow('信息修改请求发送失败');
                promptWindow.init();
            }
        });
    }

    //输入错误提示
    function errorTip($modalInput, tip) {
        $modalInput.focus();
        $modalInput.val('');
        $modalInput.attr('placeholder', tip);
    }

    //字符串转驼峰形式
    function toHumpString(string) {
        var arr = string.split('-');
        for (var i = 1; i < arr.length; i++) {
            var curStr = arr[i];
            arr[i] = curStr.replace(curStr[0], curStr[0].toUpperCase());
        }
        return arr.join('');
    }

    //用户输入检测
    function checkInput(key, value, $modalInput) {
        if (value === '') {
            errorTip($modalInput, '输入为空!');
            return false;
        }
        var flag = true;
        switch (key) {
            case 'realName':
                var reg = /^[\u4E00-\u9FA5]+$/;
                if (!reg.test(value)) {
                    errorTip($modalInput, '姓名格式错误!');
                    flag = false;
                }
                break;
            case 'idCode':
                var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                if (!reg.test(value)) {
                    errorTip($modalInput, "身份证号格式错误!");
                    flag = false;
                }
                break;
            case 'phone':
                var reg = /^1[34578]\d{9}$/;
                if (!reg.test(value)) {
                    errorTip($modalInput, "手机号格式错误!");
                    flag = false;
                }
                break;
            case 'email':
                var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
                if (!reg.test(value)) {
                    errorTip($modalInput, "邮箱格式错误!");
                    flag = false;
                }
                break;
        }
        return flag;
    }

    //密码输入框判空
    function pswIsEmpty($oldPsw, $newPsw) {
        var $inputPsw = $oldPsw.add($newPsw);
        for (var i = 0; i < $inputPsw.length; i++) {
            $inputPsw.get(i).oninput = debounce(function () {
                var oldVal = $oldPsw.val();
                var newVal = $newPsw.val();
                if (!oldVal) {
                    $oldPsw.focus();
                    flag = false;
                    console.log(flag);
                } else if (!newVal) {
                    $newPsw.focus();
                    flag = false;
                    console.log(flag);
                } else {
                    flag = true;
                    console.log(flag);
                }
            },300);
        }
    }

    //发送密码修改请求
    function sendPswModify(obj) {
        $.ajax('/busposition/user/modify/pwd', {
            type: 'post',
            data: obj,
            // contentType: 'application/json;charset=utf-8',
            success: function (data) {
                if (data.success === true) {
                    var promptWindow = new PromptWindow(data.message);
                    promptWindow.init();
                } else {
                    var promptWindow = new PromptWindow(data.message);
                    promptWindow.init();
                }
            },
            error: function () {
                var promptWindow = new PromptWindow('密码修改请求发送失败');
                promptWindow.init();
            }
        })
    }

    //设置列表按下抬起样式
    function setDownUpStyle($eles){
        for (var i = 0; i < $eles.length; i++) {
            $eles.get(i).addEventListener('touchstart', function () {
                $(this).css({
                    'background-color': '#ccc',
                })
            }, false);
            $eles.get(i).addEventListener('touchend', function () {
                $(this).css({
                    'background-color': '#fff',
                })
            }, false);
        }
    }

};