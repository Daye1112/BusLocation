function Route(map,routeMessage){
    this.map = map;
    this.endTime = '';
    this.routeId = 0;
    this.routeName = '';
    this.startTime = '';
    this.stationBothMarker = [];
    this.stationInfo = [];
    this.stationLabels = [];
    this.stationMarkers = [];
    this.routeMarker = null;
    this.stationNum = 0;
    this.trackRoute = null;
    this.curDibiao = null;

    var self = this;



    //创建图标
    //point,iconUrl,sizeX,sizeY,offsetX,offsetY,rotate = 0
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
    };
    //创建站点图标和站点标注
    this.createStaionMarkerAndLabel = function() {
        // var stations = self.stationInfo;
        for (var i = 0; i < self.stationNum; i++) {
            var station = self.stationInfo[i];
            //添加站点图标
            var point = new BMap.Point(station.longitude, station.latitude);
            var options = {
                point: point,
                iconUrl: './images/zhandian.png',
                sizeX: 12,
                sizeY: 12,
                imageOffsetX: -12,
                imageOffsetY: -139,
                offsetX: 6,
                offsetY: 6,
                zindex: 1
            };
            var stationMarker = self.createMarker(options);
            self.stationMarkers.push(stationMarker);
            //添加站点标注
            // stationMarker.index = i; //添加索引
            var size = new BMap.Size(15, -5);
            var label = new BMap.Label(station.stationName, {
                offset: size,
                position: point
            });
            label.setStyle({
                border: 'none',
                backgroundColor: 'rgba(0,0,0,0)',
                color: '#333'
            });
            self.stationLabels.push(label);
        }
    };
    //创建起点终点图标
    this.createBothMarker = function() {
        var stations = self.stationInfo;
        var startPoint = new BMap.Point(stations[0].longitude, stations[0].latitude);
        var endPoint = new BMap.Point(stations[self.stationNum - 1].longitude, stations[self.stationNum - 1].latitude);
        var startOptions = {
            point: startPoint,
            iconUrl: './images/poi-marker.png',
            sizeX: 26,
            sizeY: 37,
            imageOffsetX: -11,
            imageOffsetY: -4,
            offsetX: 13,
            offsetY: 34,
            zindex: 3
        };
        var startMarker = self.createMarker(startOptions);
        startMarker.index = 0;
        self.stationBothMarker.push(startMarker);
        var endOptions = {
            point: endPoint,
            iconUrl: './images/poi-marker.png',
            sizeX: 26,
            sizeY: 37,
            imageOffsetX: -112,
            imageOffsetY: -4,
            offsetX: 13,
            offsetY: 34,
            zindex: 3
        };
        var endMarker = self.createMarker(endOptions);
        endMarker.index = self.stationNum - 1;
        self.stationBothMarker.push(endMarker);
    };
    //初始化属性
    this.initProperty = function(){
        for(var item in routeMessage){
            self[item] = routeMessage[item];
        }
        self.trackRoute = new TrackRoute(self.map,self.routeId);
        self.stationNum = self.stationInfo.length;
        self.createStaionMarkerAndLabel();
        self.createBothMarker();
    };
    //添加站点图标
    this.addStaionMarkerAndLabel = function(){
        for(var i = 0; i < self.stationNum; i++){
            self.map.addOverlay(self.stationMarkers[i]);
            self.map.addOverlay(self.stationLabels[i]);
        }
    };
    //添加起终点图标
    this.addBothMarker = function(){
        for(var i = 0; i < self.stationBothMarker.length; i++){
            self.map.addOverlay(self.stationBothMarker[i]);
        }
    };
    //创建标注路线
    this.createRouteMarker = function(callback){
        $.ajax('/busposition/routeposition/find/one',{
            type: 'post',
            data: {routeId:self.routeId},
            success: function(data){
                if(data.success === true && data.data.routePosition.length > 0){
                    //转坐标点
                    var routePositon = data.data.routePosition;
                    var routePoints = [];
                    for (var i = 0; i < routePositon.length; i++) {
                        var point = new BMap.Point(routePositon[i].longitude, routePositon[i].latitude);
                        routePoints.push(point);
                    }
                    //创建标注路线
                    self.routeMarker =  new BMap.Polyline(routePoints, {
                        strokeColor: "#534cef",
                        strokeWeight: 3,
                        strokeOpacity: 1
                    });
                    callback && callback();
                }else if(data.success === false){
                    var promptWindow = new PromptWindow(data.message);
                    promptWindow.init();
                }
            }
        })
    };
    //添加地标
    this.addDibiao = function(point){
        //移除地标
        self.map.removeOverlay(self.curDibiao);
        //创建地标
        var options = {
            point: point,
            iconUrl: './images/poi-marker.png',
            sizeX: 26,
            sizeY: 37,
            imageOffsetX: -61,
            imageOffsetY: -4,
            offsetX: 13,
            offsetY: 34,
            zindex: 3
        };
        self.curDibiao =  self.createMarker(options);
        //添加地标
        self.map.addOverlay(self.curDibiao);
    };
    //监听地图缩放事件
    this.mapZoomListener = function(){
        BMapLib.EventWrapper.addListener(self.map, 'zoomend', function(){
            //显示图标内容
            if (self.map.getZoom() >= 14) {
                for(var i = 1; i < self.stationNum - 1; i++){
                    self.stationLabels[i].show();
                }
            } else {
                for(var i = 1; i < self.stationNum - 1; i++){
                    self.stationLabels[i].hide();
                }
            }
        });
    };

    this.init = function(){
        self.initProperty();
        self.mapZoomListener();
    };


}
