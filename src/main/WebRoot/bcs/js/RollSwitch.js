function RollSwitch($targetEle,aContents){
    this.$targetEle = $targetEle;
    this.$box = $('<div class="rollswitch-box"></div>');
    this.$list = $('<ul class="rollswitch-list"></ul>');
    this.$btnsBox = $('<div class="rollswitch-btns"></div>');
    this.$prev = $('<div class="rollswitch-prev">上一站</div>');
    this.$next = $('<div class="rollswitch-next">下一站</div>');
    this.aContents = aContents;
    this.itemNum = aContents.length;
    this.itemWidth = $targetEle.innerWidth();

    this.minLeft = - (this.itemNum - 1) * this.itemWidth;
    this.curIndex = 0;
    var self = this;

    //添加结构到页面
    this.render = function(){
        self.$targetEle.append(self.$box);
        self.$box.append(self.$list);
        self.$box.append(self.$btnsBox);
        self.$btnsBox.append(self.$prev);
        self.$btnsBox.append(self.$next);
        for(var i = 0; i < self.itemNum; i++){
            var $li = self.createListItem(self.aContents[i]);
            self.$list.append($li);
        }
        //设置列表宽度
        self.$list.css({'width':self.itemWidth*self.itemNum});
        //设置显示盒子宽度
        self.$box.css({'width':self.itemWidth});
    };
    //创建列表内容
    this.createListItem = function(obj){
        var $li = $(`<li></li>`);
        var $h3 = $(`<h3>${obj.title}</h3>`);
        var $p = $(`<p>${obj.text}</p>`);
        $li.append($h3);
        $li.append($p);
        $li.css({'width':self.itemWidth});
        return $li;
    };
    //设置手机按钮按下样式
    this.setDownStyle = function(){
        var $btns = self.$prev.add(self.$next);
        for (var i = 0; i < $btns.length; i++) {
            $btns.get(i).addEventListener('touchstart', function () {
                this.style.backgroundColor = '#ccc';
                this.style.color = '#fff';
            });
            $btns.get(i).addEventListener('touchend', function () {
                this.style.backgroundColor = '#fff';
                this.style.color = '#7c7c7c';
            });
        }
    };
    //监听next,prev按钮点击事件
    this.btnsClickListener = function(callback){
        if(callback){
            self.$next.get(0).onclick = debounce(function () {
                self.moveRollWindow(self.curIndex + 1,callback);
            }, 350);
            self.$prev.get(0).onclick = debounce(function () {
                self.moveRollWindow(self.curIndex - 1,callback);
            }, 350);
        }else {
            self.$next.get(0).onclick = debounce(function () {
                self.moveRollWindow(self.curIndex + 1);
            }, 350);
            self.$prev.get(0).onclick = debounce(function () {
                self.moveRollWindow(self.curIndex - 1);
            }, 350);
        }

    };
    //移动列表
    this.moveRollWindow = function(index,callback) {
        var oldLeft = parseInt(self.$list.css('left'));
        var left = oldLeft +  (self.curIndex - index) * self.itemWidth;
        if(left < self.minLeft){
            left = self.minLeft;
            self.curIndex = self.itemNum - 1;
        }else if(left > 0){
            left = 0;
            self.curIndex = 0;
        }else {
            self.curIndex = index;
        }
        callback && callback();
        self.$list.stop().animate({'left': left + 'px'}, 300, 'swing');
    };
    //显示列表
    this.show = function(callback){
        self.$box.stop().slideDown(300);
        callback && callback();
    };
    //隐藏列表
    this.hide = function(callback){
        self.$box.stop().slideUp(300);
        callback && callback();
    };
    //删除列表
    this.bomb = function(){
        self.$box.remove();
    };
    //初始化
    this.init = function(){
        self.render();
        self.setDownStyle();
        self.btnsClickListener();
    }
}