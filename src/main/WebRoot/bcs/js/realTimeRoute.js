function initialize() {
    //0.获取所需元素
    var $header = $('.header');
    var $footer = $('.footer');
    var $title = $('.header h3');
    var $bodyIn = $('.body-in');
    var $main = $('.main');
    var $map = $('#map');
    var $btnsBox = $('.main .btns');
    var $btnsSpan = $btnsBox.find('span');
    var $enlargeBtn = $('.main .enlarge-btn');
    var $link = $('.header a');
    var $narrowBtn = $('.main .narrow-btn');
    var $posBtn = $('.main .pos-btn');
    var $backBtn = $('.header .back');
    var $my = $('.my');
    var $routeBtn = $('.main .route');
    var height = $(window).height();
    var headerHeight = $header.outerHeight();
    var footerHeight = $footer.outerHeight();

    var map = null;
    var routes = [];
    var scrollList = null;
    var rollSwitch = null;
    var curRoute = null;
    var timer = null;

    //1.初始化页面设置
    initPage($main, $map, $link, $backBtn, $my, height, headerHeight, footerHeight);
    //2.初始化地图
    map = initMap();
    //3.创建路线对象数组
    var routeId = $.getCookie('routeId');

    routes = createRoutes();
    console.log(routes);
    //4.初始化路线滚动列表
    scrollList = initScrollList(routes, $bodyIn, $routeBtn);
    //5.初始化当前路线
    curRoute = initCurRoute(routes, $title);
    //6.设置路线名
    $title.text(curRoute.routeName);
    //7.初始化站点滚动切换窗
    rollSwitch = initRollSwitch($bodyIn, $map, curRoute);
    map.panTo(curRoute.stationMarkers[0].getPosition());
    //8.初始化当前站点
    initCurStation(rollSwitch, curRoute);
    //9.初始化标注
    curRoute.addStaionMarkerAndLabel();
    curRoute.addBothMarker();
    //10.初始化当前位置
    curRoute.trackRoute.latestPoint();
    timer = setInterval(function() {
        curRoute.trackRoute.latestPoint();
    }, 2000);
    //11.初始化当前路线
    // map.addOverlay(curRoute.routeMarker);

    //9.监听路线滚动列表点击事件
    scrollListItemClick(scrollList, rollSwitch, routes, curRoute, $bodyIn, $map, $posBtn, $title, timer);

    //10.初始化按钮组
    initBtns(map, curRoute, $btnsSpan, $enlargeBtn, $narrowBtn, $posBtn);

    //初始化页面设置
    function initPage($main, $map, $link, $backBtn, $my, height, headerHeight, footerHeight) {
        //设置main和#map的高
        $main.css({ 'height': height - headerHeight - footerHeight });
        $map.css({ 'height': height - headerHeight - footerHeight });
        //阻止浏览器默认下拉事件
        document.body.addEventListener('touchmove', function(evt) {
            evt.preventDefault();
        }, false);
        //监听列表按钮点击事件
        $link.click(function() {
            location.href = './stationDetails.html';
        });
        //监听回退按钮点击事件
        $backBtn.click(function() {
            history.go(-1);
        });
        //监听我的按钮点击事件
        $my.click(function() {
            location.href = './wode.html';
        });
    }
    //初始化地图
    function initMap() {
        var map = new BMap.Map('map');
        var point = new BMap.Point(116.404, 39.915);
        map.centerAndZoom(point, 11);
        map.enableScrollWheelZoom();
        map.setMinZoom(10);
        return map
    }
    //创建路线对象数组
    function createRoutes() {
        var routeMessages = routeMessageRequest();
        var routes = [];
        var id = $.getCookie('routeId');
        if (id === '') {
            id = 0;
        } else {
            id = parseInt(id);
        }
        for (var i = 0; i < routeMessages.length; i++) {
            var route = new Route(map, routeMessages[i]);
            route.init();
            (function(route) {
                route.createRouteMarker(function() {
                    console.log(route);
                    if (route.routeId === id)
                        map.addOverlay(route.routeMarker);
                });
            })(route);
            routes.push(route);
        }
        return routes;
    }
    //初始化当前路线
    function initCurRoute(routes) {
        //设置当前路线
        var routeId = $.getCookie('routeId');
        var curRoute = null;
        if (routeId === '') {
            curRoute = routes[0];
            setCookie(curRoute.routeId);
        } else {
            var id = parseInt(routeId);
            for (var i = 0; i < routes.length; i++) {
                if (routes[i].routeId === id) {
                    curRoute = routes[i];
                }
            }
        }
        return curRoute;
    }
    //初始化当前站点
    function initCurStation(rollSwitch, curRoute) {
        //获取站点索引
        var stationId = $.getCookie('stationId');
        if (stationId === '') return;
        //打开滚动切换窗
        if (rollSwitch.$box.data('isShow') === 'false') {
            rollSwitch.show(function() { upMapControls($btnsBox, rollSwitch, $footer) });
            rollSwitch.$box.data('isShow', 'true');
        }
        //添加地标
        if (parseInt(stationId) !== 0 && parseInt(stationId) !== curRoute.stationNum - 1)
            curRoute.addDibiao(curRoute.stationMarkers[stationId].getPosition());
        //切换选项
        rollSwitch.moveRollWindow(stationId);
        //设置中心位置
        map.panTo(curRoute.stationMarkers[stationId].getPosition());
        //删除cookie
        $.delCookie('stationId', '/');
    }
    //初始化路线滚动列表
    function initScrollList(routes, $bodyIn, $routeBtn) {
        //获取路线名
        var aRouteNames = [];
        for (var i = 0; i < routes.length; i++) {
            aRouteNames[i] = routes[i].routeName;
        }
        //创建路线列表
        var scrollList = new ScrollList($bodyIn, aRouteNames);
        scrollList.init();
        $routeBtn.click(function() {
            scrollList.show();
        });
        //绑定路线id
        for (var i = 0; i < routes.length; i++) {
            var routeId = routes[i].routeId;
            var $scrollItem = scrollList.$scrollItems.eq(i);
            $scrollItem.data('routeId', routeId);
        }
        return scrollList;
    }
    //初始化站点滚动切换窗
    function initRollSwitch($bodyIn, $map, curRoute) {
        //5.1创建滚动切换窗
        var rollSwitch = createRollSwitch($bodyIn, curRoute);
        rollSwitch.render();
        rollSwitch.setDownStyle();
        //5.2监听地图点击事件
        mapClickListener($map, rollSwitch);
        //5.3监听站点标注点击事件
        stationClickListener(map, curRoute, rollSwitch);
        //5.4监听起终标注点击事件
        startEndClickListener(curRoute, rollSwitch);
        //5.5监听next,prev按钮点击事件
        rollSwitch.btnsClickListener(function() { nextPrevClickListener(map, curRoute, rollSwitch) });
        return rollSwitch;
    }
    //监听滚动列表选项点击事件
    function scrollListItemClick(scrollList, rollSwitch, routes, curRoute, $bodyIn, $map, $posBtn, $title, timer) {
        var $items = scrollList.$scrollItems;
        $items.click(function() {
            //获取路线id
            var routeId = $(this).data('routeId');
            //重置当前路线
            for (var i = 0; i < routes.length; i++) {
                var route = routes[i];
                if (route.routeId === routeId)
                    curRoute = route;
            }
            //设置路线名
            $title.text(curRoute.routeName);
            //设置cookie
            setCookie(routeId);
            //删除图标
            map.clearOverlays();
            //添加图标
            curRoute.addStaionMarkerAndLabel();
            curRoute.addBothMarker();
            //移动到起点位置
            map.panTo(curRoute.stationBothMarker[0].getPosition());
            //添加路线
            map.addOverlay(curRoute.routeMarker);
            //关闭上一个定时器
            clearInterval(timer);
            //刷新定位
            timer = setInterval(function() {
                curRoute.trackRoute.latestPoint();
            }, 2000);
            //删除滚动切换列表
            rollSwitch.bomb();
            //初始化滚动切换列表
            rollSwitch = initRollSwitch($bodyIn, $map, curRoute);
            //打开滚动切换窗
            if (rollSwitch.$box.data('isShow') === 'false') {
                rollSwitch.show(function() { upMapControls($btnsBox, rollSwitch, $footer) });
                rollSwitch.$box.data('isShow', 'true');
            }
            //重新绑定定位按钮
            positionClickListener(map, curRoute, $posBtn);
        })
    }
    //初始化按钮组件
    function initBtns(map, curRoute, $btnsSpan, $enlargeBtn, $narrowBtn, $posBtn) {
        setDownUpStyle($btnsSpan);
        enlargeClickListener(map, $enlargeBtn);
        narrowClickListener(map, $narrowBtn);
        positionClickListener(map, curRoute, $posBtn);
    }

    //获取路线对象
    function routeMessageRequest() {
        var routeMessage = null;
        // $.ajax('/busposition/bus/route/station', {
        $.ajax('/busposition/route/list', {
            type: 'get',
            success: function(data) {
                if (data.success === true) {
                    routeMessage = data.data;
                } else {
                    var promptWindow = new PromptWindow(data.message);
                    promptWindow.init();
                }
            },
            error: function() {
                var promptWindow = new PromptWindow('获取站点请求失败');
                promptWindow.init();
            },
            async: false
        });
        return routeMessage;
    }
    //添加路线对象属性
    function initRouteProperty(routeMessage) {
        for (var i = 0; i < routeMessage.length; i++) {
            var item1 = routeMessage[i];
            item1.stationMarkers = [];
            item1.stationLabels = [];
            item1.stationBothMarker = [];
            item1.stationNum = item1.stationInfo.length;

            item1.trackMarkers = [];
            item1.trackIndex = 0;
            item1.trackRoute = [];
            item1.curArrowMarker = null;
            addStaionMarkerAndLabel(item1);
            addBothMarker(item1);
        }
    }
    //监听地图缩放事件
    function mapZoomListener(map) {
        BMapLib.EventWrapper.addListener(map, 'zoomend', function() {
            //显示图标内容
            if (this.getZoom() >= 14) {
                for (var i = 1; i < route.stationMarkers.length - 1; i++) {
                    route.stationMarkers[i].getLabel().show();
                }
            } else {
                for (var i = 1; i < route.stationMarkers.length - 1; i++) {
                    route.stationMarkers[i].getLabel().hide();
                }
            }
        });
    }
    //创建站点滚动切换窗
    function createRollSwitch($bodyIn, curRoute) {
        //创建滚动切换窗
        var aContents = [];
        for (var i = 0; i < curRoute.stationInfo.length; i++) {
            var station = curRoute.stationInfo[i];
            var obj = {
                title: station.stationName,
                text: '最晚发车时间：' + station.departTime
            };
            aContents.push(obj);
        }
        var rollSwitch = new RollSwitch($bodyIn, aContents);
        //添加显示标志
        rollSwitch.$box.data('isShow', 'false');
        return rollSwitch;
    }
    //监听地图点击事件
    function mapClickListener($map, rollSwitch) {
        if (IsPC()) {
            $map.get(0).addEventListener('click', function() {
                if (rollSwitch.$box.data('isShow') === 'true') {
                    rollSwitch.hide(function() { downMapControls($btnsBox) });
                    rollSwitch.$box.data('isShow', 'false');
                }
            }, false);
        } else {
            $map.get(0).addEventListener('touchend', function() {
                if (rollSwitch.$box.data('isShow') === 'true') {
                    rollSwitch.hide(function() { downMapControls($btnsBox) });
                    rollSwitch.$box.data('isShow', 'false');
                }
            }, false);
        }
    }
    //监听站点标注点击事件
    function stationClickListener(map, curRoute, rollSwitch) {
        for (var i = 1; i < curRoute.stationMarkers.length - 1; i++) {
            var stationMarker = curRoute.stationMarkers[i];
            (function(i) {
                stationMarker.addEventListener('click', function(event) {
                    event = event || window.event;
                    //打开滚动切换窗
                    if (rollSwitch.$box.data('isShow') === 'false') {
                        rollSwitch.show(function() { upMapControls($btnsBox, rollSwitch, $footer) });
                        rollSwitch.$box.data('isShow', 'true');
                    }
                    //添加地标
                    curRoute.addDibiao(this.getPosition());
                    //切换选项
                    rollSwitch.moveRollWindow(i);
                    event.domEvent.stopPropagation();
                    //设置中心位置
                    map.panTo(this.getPosition());
                });
            })(i);
        }
    }
    //监听起终标注点击事件
    function startEndClickListener(curRoute, rollSwitch) {
        for (var i = 0; i < curRoute.stationBothMarker.length; i++) {
            var stationMarker = curRoute.stationBothMarker[i];

            stationMarker.addEventListener('click', function(event) {
                event = event || window.event;
                if (rollSwitch.$box.data('isShow') === 'false') {
                    rollSwitch.show(function() { upMapControls($btnsBox, rollSwitch, $footer) });
                    rollSwitch.$box.data('isShow', 'true');
                }
                rollSwitch.moveRollWindow(parseInt(this.index));
                event.domEvent.stopPropagation()
            }, true);
        }
    }
    //监听next,prev按钮点击事件(callback)
    function nextPrevClickListener(map, curRoute, rollSwitch) {
        //获取当前站点索引
        var index = rollSwitch.curIndex;
        //获取索引对应站点标注
        var marker = curRoute.stationMarkers[index];
        //移动到标注位置
        var point = marker.getPosition();
        map.panTo(point);
        //添加地标
        if (index !== 0 && index !== curRoute.stationNum - 1) {
            curRoute.addDibiao(point);
        } else {
            map.removeOverlay(curRoute.curDibiao);
            curRoute.curDibiao = null;
        }
    }

    //监听按钮组按下抬起事件
    function setDownUpStyle($btnsSpan) {
        for (var i = 0; i < $btnsSpan.length; i++) {
            $btnsSpan.get(i).addEventListener('touchstart', function() {
                $(this).css({
                    'backgroundColor': '#ccc',
                })
            }, false);
            $btnsSpan.get(i).addEventListener('touchend', function() {
                $(this).css({
                    'backgroundColor': '#fff',
                })
            }, false);
        }
    }
    //监听放大按钮点击事件
    function enlargeClickListener(map, $enlargeBtn) {
        $enlargeBtn.click(function() {
            var zoom = map.getZoom() + 1;
            zoom = zoom >= 19 ? 19 : zoom;
            map.setZoom(zoom);
        });
    }
    //监听缩小按钮点击事件
    function narrowClickListener(map, $narrowBtn) {
        $narrowBtn.click(function() {
            var zoom = map.getZoom() - 1;
            zoom = zoom <= 10 ? 10 : zoom;
            map.setZoom(zoom);
        });
    }
    //监听位置按钮点击事件
    function positionClickListener(map, curRoute, $posBtn) {
        $posBtn.get(0).onclick = function() {
            var point;
            //没有轨迹移动到起点
            if (!curRoute.trackRoute.curArrowMarker) {
                point = curRoute.stationBothMarker[0].getPosition();
                map.panTo(point);
            } else {
                //有位置移动到箭头
                point = curRoute.trackRoute.curArrowMarker.getPosition();
                map.panTo(point);
            }
        };
    }
    //上移按钮组
    function upMapControls($btnsBox, rollSwitch, $footer) {
        var rollHeight = 120;
        var footerHeight = $footer.innerHeight();
        var dis = rollHeight - footerHeight;
        console.log('rollHeight:' + rollHeight);
        console.log('dis:' + dis);
        var oldBottom = parseInt($btnsBox.css('bottom'));
        console.log('bottom:' + oldBottom);
        var bottom = oldBottom + dis;
        console.log('bottom:' + bottom);
        $btnsBox.stop().animate({ 'bottom': bottom + 'px' }, 300);
    }
    //下移按钮组
    function downMapControls($btnsBox) {
        $btnsBox.stop().animate({ 'bottom': 15 + 'px' }, 300);
    }
    //设置cookie（routeId）
    function setCookie(routeId) {
        $.addCookie({
            key: 'routeId',
            value: routeId,
            path: '/'
        })
    }
}
//异步加载百度api
function loadScript() {
    var script = document.createElement("script");
    script.src = "http://api.map.baidu.com/api?v=2.0&ak=Hn8yCUyLlsPxzhCIdG8wETGiGtViLCHI&callback=initialize";
    document.body.appendChild(script);
}
//在所有内容加载完毕后执行
window.onload = loadScript;