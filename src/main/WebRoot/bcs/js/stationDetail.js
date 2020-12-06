window.onload = function(){
    //获取所需元素
    var $header = $('.header');
    var $link = $('.header a');
    var $backBtn = $('.header .back');
    var $title = $('.header h3');
    var $firstStation = $('.main .first-station');
    var $lastStation = $('.main .lastStation');
    var $operationTime = $('.main .operation-time');
    var $listUl = $('.main .station-list');
    var $footer = $('.footer');
    var $stationTitle = $('.main .station-title');
    var $stationContent = $('.main .station-content');
    var $my = $('.my');
    var $arrows = null;
    var $listItems = null;

    var routeMessage = null;
    var routeId = parseInt($.getCookie('routeId'));
    var route = null;
    var stations = null;

    var height = $(window).height();
    var headHeight = $header.outerHeight();
    var footHeight = $footer.outerHeight();
    var stationTitleHeight = $stationTitle.outerHeight();
    var stationContentHeight = height - headHeight - footHeight - stationTitleHeight;
    var $curItem = null;
    var listItemHeight = 0;
    //1.监听地图按钮点击事件
    $link.click(function(){
        location.href = './realTimeRoute.html';
    });
    //2.监听回退按钮点击事件
    $backBtn.click(function(){
        history.go(-1);
    });
    //3.获取数据
    $.ajax('/busposition/bus/route/station',{
        type: 'get',
        success: function(data){
            if(data.success === true){
                routeMessage = data.data;
            }else {
                var promptWindow = new PromptWindow(data.message);
                promptWindow.init();
            }
        },
        error: function(){
            var promptWindow = new PromptWindow('站点信息请求发送失败');
            promptWindow.init();
        },
        async: false
    });
    //4.设置route,routeId,stations
    for(var i = 0; i < routeMessage.length; i++){
        if(routeMessage[i].routeId === routeId){
            route = routeMessage[i];
            stations = route.stationInfo;
        }
    }
    //5.设置标题
    $title.text(route.routeName);
    //6.设置路线
    $firstStation.text(stations[0].stationName);
    $lastStation.text(stations[stations.length - 1].stationName);
    //7.设置运营时间
    $operationTime.text(route.startTime + '-' + route.endTime);
    //8.设置内容区域高度
    $stationContent.css({
        'height': stationContentHeight + 'px'
    });
    //9.初始化站点列表&列表项高度
    $listItems = initStations($listUl,stations);
    listItemHeight = $listItems.eq(0).outerHeight();
    //10.设置列表高度
    setListHeight($listUl,listItemHeight,$curItem);
    //11.监听列表滚动事件
    addScroll($listUl,stationContentHeight);
    //12.监听箭头按钮点击事件
    $arrows = $('.arrow');
    addArrowsClick($arrows,$curItem,$listUl,listItemHeight,stationContentHeight);
    //13.监听列表项点击事件
    addItemsClick($listItems);
    //14.监听我的按钮点击事件
    $my.click(function(){
        location.href = './wode.html';
    })
};
//设置列表项点击事件
function addItemsClick($listItems){
    $listItems.click(function(event){
        var id = $(this).data('id');
        $.addCookie({
            key: 'stationId',
            value: id,
            path: '/'
        });
        location.href = './realTimeRoute.html';
    })
}
//初始化站点列表项
function initStations($listUl,stations){
    //清空列表
    $listUl.empty();
    //创建列表项
    for(var i = 0; i < stations.length; i++){
        createListItem($listUl,stations[i],i);
    }
    return $listUl.children('li');
}
//创建并添加列表项
function createListItem($listUl,station,id){
    var $item = $(`<li data-id="${id}">
                    <h3>
                        ${station.stationName}
                        <i class="arrow"></i>
                    </h3>
                    <div class="detail">
                        <p>最晚发车时间：<span>${station.departTime}</span></p>
                        <p>下一站：<span>${station.nextStation}</span></p>
                    </div>
                </li>`);
    $listUl.append($item);
}
//添加箭头点击事件
function addArrowsClick($arrows,$curItem,$listUl,listItemHeight,stationContentHeight){
    $arrows.click(function(event){
        var $item = $(this).parents('li');
        var listHeight;
        //如果存在当前项
        if($curItem){
            //如果当前项=点击项
            if($curItem.data('id') !== $item.data('id')){
                $curItem.removeClass('cur');
                $item.addClass('cur');
                $curItem = $item;
                setListHeight($listUl,listItemHeight,$curItem);
                addScroll($listUl,stationContentHeight);
            } else {
                //如果当前项!=点击项
                $item.removeClass('cur');
                $curItem = null;
                setListHeight($listUl,listItemHeight,$curItem);
                addScroll($listUl,stationContentHeight);
            }
        } else {
            // 如果不存在当前项
            $item.addClass('cur');
            $curItem = $item;
            setListHeight($listUl,listItemHeight,$curItem);
            addScroll($listUl,stationContentHeight);
        }
        event.stopPropagation();
    })
}
//设置列表高度
function setListHeight($listUl,listItemHeight,$curItem){
    var num = $listUl.children('li').length;
    var curItemHeight = $curItem === null ? listItemHeight : $curItem.outerHeight();
    var height = listItemHeight * (num - 1) + curItemHeight;
    $listUl.css({'height':height + 'px'});
}
//设置滚动事件
function addScroll($listUl,stationContentHeight){
    if(IsPC())
        addPcScroll($listUl,stationContentHeight);
    else{
        addPhoneScroll($listUl,stationContentHeight);
    }
}
//设置电脑列表滚动事件
function addPcScroll($listUl,stationContentHeight){
    var oListUl = $listUl.get(0);
    var minTop = stationContentHeight - $listUl.outerHeight() - 10;
    var isDrag = false;
    var oldY;
    var newY;
    var oldTop;
    oListUl.addEventListener('mousedown',function(event){
        event = event || window.event;
        oldY = event.clientY;
        oldTop = parseInt($(this).css('top'));
        isDrag = true;
        event.stopPropagation();
    },true);
    oListUl.addEventListener('mousemove',function(event){
        if(isDrag){
            event = event || window.event;
            var newY = event.clientY;
            var dis = newY - oldY + oldTop;
            dis = dis < minTop ? minTop : dis;
            dis = dis > 10 ? 10 : dis;
            $listUl.css({'top': dis + 'px'});
            event.stopPropagation();
        }
    },true);
    document.addEventListener('mouseup',function(event){
        event = event || window.event;
        newY = event.clientY;
        isDrag = false;
        event.stopPropagation();
    },true);
    oListUl.addEventListener('click',function(event){
        event = event || window.event;
        if(newY !== oldY)
            event.stopPropagation();
    },true);
}
//设置手机列表滚动事件
function addPhoneScroll($listUl,stationContentHeight){
    var oListUl = $listUl.get(0);
    var minTop = stationContentHeight - $listUl.outerHeight() - 10;
    var isDrag = false;
    var oldY;
    // var newY;
    var oldTop;
    oListUl.addEventListener('touchstart',function(event){
        event = event || window.event;
        var touch = event.changedTouches.item(0);
        oldY = touch.clientY;
        oldTop = parseInt($(this).css('top'));
        isDrag = true;
        event.stopPropagation();
    },true);
    oListUl.addEventListener('touchmove',function(event){
        if(isDrag){
            event = event || window.event;
            var touch = event.changedTouches.item(0);
            var newY = touch.clientY;
            var dis = newY - oldY + oldTop;
            dis = dis < minTop ? minTop : dis;
            dis = dis > 10 ? 10 : dis;
            $listUl.css({'top': dis + 'px'});
            event.stopPropagation();
            event.preventDefault();
        }
    },true);
    document.addEventListener('touchend',function(event){
        event = event || window.event;
        var touch = event.changedTouches.item(0);
        // newY = touch.clientY;
        isDrag = false;
        event.stopPropagation();
    },true);
    // oListUl.addEventListener('click',function(event){
    //     event = event || window.event;
    //     if(newY !== oldY)
    //         event.stopPropagation();
    // },true);
}