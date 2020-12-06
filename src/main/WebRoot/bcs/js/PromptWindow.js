function PromptWindow(test) {
        this.test = test;
        this.$messageBox = $(`<div class="message-box">${test}</div>`);
        var self = this;

        this.render = function(){
            self.$messageBox.css({
                'height': '30px',
                'line-height': '30px',
                'padding': '0 10px',
                'border-radius': '3px',
                'background-color': '#000',
                'color': '#fff',
                'font-size': '14px',
                'display': 'inline-block',
                'position': 'absolute',
                'left': '50%',
                'bottom': '-30px',
                'transform': 'translateX(-50%)',
                'opacity':'0.5'
            });
            $('body').append(self.$messageBox);
        };
        this.pop = function(callback){
            $(self.$messageBox).animate({'bottom':'50px'},'fast','swing')
                .delay(2000).fadeOut('fast','swing',callback);
        };
        this.bomb = function(){
            self.$messageBox.remove();
        };

        //初始化
        this.init = function(){
            self.render();
            self.pop(self.bomb);
        }
}
