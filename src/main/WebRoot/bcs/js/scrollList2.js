function ScrollList($targetEle,aContents,itemHeight,maxNum){
    this.$targetEle = $targetEle;
    this.$scrollMask = $('<div class="scroll-mask"></div>');
    this.$scrollBox = $('<div class="scroll-box"></div>');
    this.$scrollList = $('<ul class="scroll-list"></ul>');
    this.$scrollItems = null;
    this.$cur = null;

    this.aContents = aContents;    //选项内容
    this.itemNum = aContents.length;  //选项个数
    this.itemHeight = itemHeight || 35;  //选项高度
    this.maxNum = maxNum || 3.5;  //最大显示列表项的数目
    var self = this;

    //渲染
    this.render = function(){
        //设置列表高度
        self.setListHeight();
        //设置显示盒子高度
        self.setBoxHeight();

        //添加元素到页面中
        for(var i = 0; i < self.itemNum; i++){
            self.addItem(self.aContents[i]);
        }
        self.$scrollItems = self.$scrollList.children('li');
        self.$scrollBox.append(self.$scrollList);
        self.$targetEle.append(self.$scrollBox);
        self.$targetEle.append(self.$scrollMask);
    };
    //添加手机按下样式
    this.setDownStyle = function(){
        for(var i = 0; i < self.itemNum;i++){
            self.$scrollItems.get(i).addEventListener('touchstart',function(){
                $(this).css({'background-color':'#ccc'});
                $(this).siblings('li').css({'background-color':'#fff'});
            })
        }
    };
    //添加手机拖动事件
    this.addPhoneDrag = function(){
        var oldY,oldTop,newY,newTop;
        var isMove;
        var topMin = parseInt(self.$scrollBox.css('height')) - parseInt(self.$scrollList.css('height'));
        var oScrollList = self.$scrollList.get(0);
        oScrollList.addEventListener('touchstart',function(touchEvent){
            //获取当前触摸对象
            isMove = false;
            var touch = touchEvent.changedTouches.item(0);
            oldY = touch.clientY;
            oldTop = parseInt($(this).css('top'));
        },true);
        oScrollList.addEventListener('touchmove',function(touchEvent){
            isMove = true;
            var touch = touchEvent.changedTouches.item(0);
            newY = touch.clientY;
            newTop = newY - oldY + oldTop;
            newTop =  newTop <= 0 ? newTop : 0;
            newTop =  newTop >= topMin ? newTop : topMin;
            $(this).css({'top':newTop});
            touchEvent.preventDefault();
        },true);
        oScrollList.addEventListener('touchend',function(touchEvent){
            if(isMove){
                touchEvent.stopPropagation();
            }
        },true);
    };
    //添加电脑拖动事件
    this.addPcDrag = function(){
        var topMin = parseInt(self.$scrollBox.css('height')) - parseInt(self.$scrollList.css('height'));
        var oldY,newY,oldTop,newTop;
        var isMove;
        var isDrag;
        var oScrollList = self.$scrollList.get(0);

        oScrollList.addEventListener('mousedown',function(event){
            isMove = false;
            event = event || window.event;
            oldY = event.clientY;
            oldTop = parseInt($(this).css('top'));
            event.stopPropagation();
            oScrollList.addEventListener('mousemove',move,true);
            function move(event){
                isMove = true;
                event = event || window.event;
                newY = event.clientY;
                newTop = newY - oldY + oldTop;
                newTop =  newTop <= 0 ? newTop : 0;
                newTop =  newTop >= topMin ? newTop : topMin;
                $(this).css({'top':newTop});
                event.stopPropagation();
            }
            oScrollList.addEventListener('mouseup',function(event){
                event = event || window.event;
                oScrollList.removeEventListener('mousemove',move,true);
                event.stopPropagation();
            },true);
        },true);
        oScrollList.addEventListener('click',function(event){
            event = event || window.event;
            if(isMove)
                event.stopPropagation();
        },true);
    };
    //添加列表项
    this.addItem = function(text){
        var $scrollItem = $(`<li class="scroll-item">${text}</li>`);
        $scrollItem.css({
            'height':self.itemHeight + 'px',
            'line-height':self.itemHeight + 'px'
        });
        self.$scrollList.append($scrollItem);
    };
    //设置列表高度
    this.setListHeight = function(){
        var height = self.itemNum * self.itemHeight;
        self.$scrollList.css({'height':height+'px'});

    };
    //设置显示盒子高度
    this.setBoxHeight = function(){
        var height = self.itemHeight * self.maxNum;
        this.$scrollBox.css({'height':height+'px'});
    };
    //打开列表
    this.show = function(){
        self.$scrollMask.stop().fadeIn(200,function(){
            self.$scrollBox.stop().slideDown(200);
        });
    };
    //关闭列表
    this.hide = function(){
        self.$scrollBox.stop().slideUp(200,function(){
            self.$scrollMask.stop().fadeOut(200);
        });
    };
    //组件初始化
    this.init = function(){
        //渲染到页面
        self.render();
        //监听遮罩点击事件
        self.$scrollMask.click(function(){
            self.hide();
        });
        //监听列表项点击事件
        self.$scrollItems.click(function(event){
            self.$cur = event.target;
            self.hide();
        });
        if(IsPC()){
            self.addPcDrag()
        } else {
            //添加手机按下样式
            self.setDownStyle();
            //添加手机滚动事件
            self.addPhoneDrag();
        }
    };
}