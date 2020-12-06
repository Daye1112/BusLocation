function TrackRoute(map,routeId){
     //latitude,longitude,velocity,direction
    this.map = map;
    this.trackMessages = [];
    this.trackMarkers = [];
    this.routeId = routeId;
    this.trackIndex = 0;
    this.curArrowMarker = null;

    var self = this;

    // "data":{"routeId":5,"trackRoute":{"latitude":30.19143822182585,"velocity":0.5,"longitude":120.82580722465978,"direction":0.0}
    //获取最新定位,并添加箭头
    this.latestPoint = function(callback){
        $.ajax('http://www.darren1112.com/busposition/bus/route/track',{
            type: 'post',
            data: {routeId:self.routeId},
            success: function(data){
                //添加箭头
                //删除箭头
                console.log('定位成功！');
                self.curArrowMarker && self.map.removeOverlay(self.curArrowMarker);
                if(data.success === true && data.data.trackRoute){
                    //添加箭头
                    console.log(data);
                    var point = new BMap.Point(data.data.trackRoute.longitude, data.data.trackRoute.latitude);
                    var options = {
                        point: point,
                        iconUrl: './images/bashi.png',
                        sizeX: 27,
                        sizeY: 27,
                        offsetX: 13.5,
                        offsetY: 13.5,
                    };
                    self.curArrowMarker = self.createMarker(options);
                    self.map.addOverlay(self.curArrowMarker);
                    //执行回调函数
                    callback && callback();
                }else if(data.success === false){
                    var promptWindow = new PromptWindow(data.message);
                    promptWindow.init();
                }
            },
            error: function(){
                var promptWindow = new PromptWindow('当前定位请求失败');
                promptWindow.init();
            }
        })
    };
    //画所有轨迹
    this.drawAll = function(isDrawArrow){
        if(self.trackMarkers.length > 0){
            //添加轨迹
            for(var i = 0; i < self.trackMarkers.length; i++){
                map.addOverlay(self.trackMarkers[i]);
            }
            //添加箭头
            if(isDrawArrow)
                self.addArrow();
        }
    };
    //发送轨迹请求，添加 新增轨迹信息 和 轨迹路线
    this.getNewMessage = function(callback){
        $.ajax('http://www.darren1112.com/busposition/route/one/position', {
            type: 'post',
            //包头不包尾
            data: {routeId: self.routeId, startIndex: self.trackIndex},
            success: function (data) {
                // console.log(`轨迹请求成功! routeId:${self.routeId} trackIndex${self.trackIndex}`);
                if (data.success === true && data.data.trackRoute.length !== 0) {
                    //添加轨迹信息
                    self.trackMessages = self.trackMessages.concat(data.data.trackRoute);
                    //获取新增轨迹图标
                    var newLine = self.getTrackMarker();
                    self.trackMarkers.push(newLine);
                    //执行回调函数
                    callback && callback();
                   /* //添加新增轨迹图标
                    self.map.addOverlay(newLine);
                    //添加箭头
                    self.addArrow();*/
                } else if(data.success === false){
                    var promptWindow = new PromptWindow(data.message);
                    promptWindow.init();
                }
            },
            error: function () {
                var promptWindow = new PromptWindow('轨迹点请求失败');
                promptWindow.init();
            },
        });
    };
    //获取轨迹标注
    //参数表：起始索引，结束索引
    //返回：轨迹标注
    this.getTrackMarker = function() {
        //1.获取轨迹点区间
        var startIndex = self.trackIndex;
        var endIndex = self.trackMessages.length - 1;
        //2.将轨迹信息转为轨迹点数组
        var tracks = self.trackMessages;
        var trackPoints = [];
        for (var i = startIndex; i <= endIndex; i++) {
            var point = new BMap.Point(tracks[i].longitude, tracks[i].latitude);
            trackPoints.push(point);
        }
        //3.修改起始索引
        self.trackIndex = endIndex;
        //4.创建轨迹图标并返回
        return new BMap.Polyline(trackPoints, {
            strokeColor: "#534cef",
            strokeWeight: 3,
            strokeOpacity: 1
        });
    };
    //添加箭头
    this.addArrow = function() {
        //如果没有轨迹
        if (self.trackId === 0) {
            //有箭头就删除
            self.curArrowMarker && map.removeOverlay(self.curArrowMarker);
            //返回
            return null;
        }
        //如果有箭头，删除
        if (self.curArrowMarker)
            map.removeOverlay(self.curArrowMarker);
        //添加箭头
        var track = self.trackMessages[self.trackMessages.length - 1];
        var point = new BMap.Point(track.longitude, track.latitude);
        var options = {
            point: point,
            iconUrl: './images/bashi.png',
            sizeX: 27,
            sizeY: 27,
            offsetX: 13.5,
            offsetY: 13.5,
        };
        self.curArrowMarker = self.createMarker(options);
        map.addOverlay(self.curArrowMarker);
    };
    //创建图标
    this.createMarker = function(options) {
        options.rotate = options.rotate || 0;
        options.imageOffsetX = options.imageOffsetX || 0;
        options.imageOffsetY = options.imageOffsetY || 0;
        options.zindex = options.zindex || 0;
        var imgOffset = new BMap.Size(options.imageOffsetX,options.imageOffsetY);
        var imgSize = new BMap.Size(options.sizeX, options.sizeY);
        var imgAnchor = new BMap.Size(options.offsetX, options.offsetY);
        var myIcon = new BMap.Icon(options.iconUrl, imgSize, {anchor: imgAnchor,imageOffset:imgOffset});
        var marker = new BMap.Marker(options.point, {icon: myIcon, rotation: options.rotate});
        marker.zIndex =options.zindex;
        // self.map.addOverlay(marker);
        return marker;
    }

    //初始化
    this.init = function(){
        self.drawLine(false);
    }
}