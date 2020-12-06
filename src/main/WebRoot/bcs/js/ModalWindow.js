function ModalWindow($targetEle){
    //1.创建遮罩
    this.$targetEle = $targetEle;
    this.$modalMask = $('<div class="modal-mask"></div>');
    this.$modalBox = $('<div class="modal-box"></div>');
    this.$modalTitle = $('<div class="modal-title"></div>');
    this.$modalContent = $('<div class="modal-content"></div>');
    this.$modalFooter = $('<div class="modal-footer"></div>');
    this.$btnCancel = $('<button class="btn-cancel">取消</button>');
    this.$btnOk = $('<button class="btn-ok">确定</button>');
    var self = this;

    //渲染
    this.render = function(){
        self.$targetEle.append(self.$modalMask);
        self.$targetEle.append(self.$modalBox);
        self.$modalBox.append(self.$modalTitle);
        self.$modalBox.append(self.$modalContent);
        self.$modalBox.append(self.$modalFooter);
        self.$modalFooter.append(self.$btnCancel);
        self.$modalFooter.append(self.$btnOk);
    };
    //打开模态窗
    this.openModalWindow = function() {
        self.$modalMask.stop().fadeIn(300);
        self.$modalBox.stop().fadeIn(300);
    };
    //关闭模态窗
    this.closeModalWindow = function(callback){
        self.$modalMask.stop().fadeOut(300,callback);
        self.$modalBox.stop().fadeOut(300,callback);
    };
    //设置模态窗按下抬起样式
    this.setBtnStyle = function() {
        var $btns = self.$btnCancel.add(self.$btnOk);
        for (var i = 0; i < $btns.length; i++) {
            $btns.get(i).addEventListener('touchstart', function () {
                // alert('被点击了');
                $(this).css({
                    'background-color': '#5bc0de',
                    'color': '#fff'
                })
            }, false);
            $btns.get(i).addEventListener('touchend', function () {
                // alert('被点击了');
                $(this).css({
                    'background-color': '#fff',
                    'color': '#5bc0de'
                })
            }, false);
        }
    };
    //删除模态窗
    this.bomb = function(){
        self.$modalMask.remove();
        self.$modalBox.remove();
    };
    //设置标题内容
    this.setTitle = function(text){
        self.$modalTitle.text(text);
    };


    //初始化
    this.init = function(){
        //添加模态窗
        self.render();
        //监听遮罩点击事件
        self.$modalMask.click(function(){
            self.closeModalWindow(self.bomb);
        });
        //监听取消按钮点击事件
        self.$btnCancel.click(function () {
            self.closeModalWindow(self.bomb);
        });
        //设置模态窗按钮按下样式
        self.setBtnStyle();
    };
    this.init();

}