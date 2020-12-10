package com.ky.gps.service.impl;

import com.ky.gps.dao.SbBusPositionDao;
import com.ky.gps.dao.SbBusPositionHisDao;
import com.ky.gps.dao.SbRouteDao;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SbBusPosition;
import com.ky.gps.service.SbBusPositionService;
import com.ky.gps.util.JudgeTimeUtil;
import com.ky.gps.util.ResultWrapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * SbBusPositionService接口的实现类
 * 对校车位置的业务处理
 */
@Service
public class SbBusPositionServiceImpl implements SbBusPositionService {

    @Resource
    private SbRouteDao sbRouteDao;
    @Resource
    private SbBusPositionDao sbBusPositionDao;
    @Resource
    private SbBusPositionHisDao sbBusPositionHisDao;

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public Map<String, Object> findByRouteId(Integer routeId) {
        return sbBusPositionDao.findByRouteId(routeId);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findNewPositionByRouteId(Integer routeId) {
        Map<String, Object> trackRoute = sbBusPositionDao.findNewPositionByRouteId(JudgeTimeUtil.getWeek(), routeId);
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("routeId", routeId);
        resultMap.put("trackRoute", trackRoute);
        return ResultWrapperUtil.setSuccessOf(resultMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePositionAndMoveToHis() {
        //将当天记录插入历史表
        sbBusPositionHisDao.insertFromSbBusPosition();
        //清空当天位置表
        sbBusPositionDao.deleteAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void savePosition(SbBusPosition sbBusPosition) {
        List<Map<String, Object>> routeList = sbRouteDao.findByGpsId(sbBusPosition.getSbGps().getId());
        for (Map<String, Object> route : routeList) {
            String sbbrWeek = route.get("sbbrWeek").toString();
            String sbbrStartTime = route.get("sbbrStartTime").toString();
            String sbbrEndTime = route.get("sbbrEndTime").toString();
            //判断路线是否在当前时间范围内
            if (JudgeTimeUtil.isEffectiveDate(sbbrStartTime, sbbrEndTime)
                    && JudgeTimeUtil.getWeek().equals(sbbrWeek)) {
                //设置定位的路线id
                sbBusPosition.setRouteId((Integer) route.get("id"));
                break;
            }
        }
        if (sbBusPosition.getRouteId() == null) {
            sbBusPosition.setRouteId(-1);
        }
        sbBusPositionDao.savePosition(sbBusPosition);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public List<Map<String, Object>> findAllPositionByBusId(String busId) {
        return sbBusPositionDao.findAllPositionByBusId(busId);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findAllEffectiveRoutePosition() {
        //创建待返回的封装数据对象
        ResultWrapper resultWrapper;
        //创建待返回的数据类型，将过滤好的数据存入该数据类型
        List<Map<String, Object>> effectiveRoutePosition = new ArrayList<>();
        //获取该星期的所有校车定位数据
        List<Map<String, Object>> allRoutePosition = sbBusPositionDao.findAllRoutePosition(JudgeTimeUtil.getWeek());
        if (0 == allRoutePosition.size()) {
            return ResultWrapperUtil.setSuccessOf(allRoutePosition);
        }//判断是否有记录
        //创建临时存放站点的定位信息，存放每个站点的所有定位信息存入该list
        List<Map<String, Object>> filterTmpPositionList = new ArrayList<>();
        //临时站点名,初始化为第一条数据的站点名
        String routeNameTmp = allRoutePosition.get(0).get("routeName").toString();
        Integer routeId = (Integer) allRoutePosition.get(0).get("routeId");
        //遍历筛选，Keys={routeName, startTime, endTime, recodeTime, longitude, latitude, velocity, direction}
        for (int i = 0; i < allRoutePosition.size(); i++) {
            //获取map
            Map<String, Object> routePositionMap = allRoutePosition.get(i);
            //判断当前时间是否在路线时间段内
//                if (!JudgeTimeUtil.isEffectiveDate(routePositionMap.get("startTime").toString().trim(),
//                        routePositionMap.get("endTime").toString().trim())
//                        || !JudgeTimeUtil.isEffectiveTimestamp((Timestamp) routePositionMap.get("recodeTime"),
//                        routePositionMap.get("startTime").toString().trim(),
//                        routePositionMap.get("endTime").toString().trim())) {
//                    //如果当前时间不在该时间段，或者定位信息的时间段不在路线时间段内，移除该记录
//                    allRoutePosition.remove(routePositionMap);
//                    i--;
//
//                } else {
            //如果在该时间段，删除不需要的数据
            routePositionMap.remove("startTime");
            routePositionMap.remove("endTime");
            routePositionMap.remove("recodeTime");
            //判断站点名是否已经被记录下来
            if (!routePositionMap.get("routeName").toString().equals(routeNameTmp)) {
                //如果未被记录下来，说明开始新的站点定位数据的记录
                addToEffectiveRoutePosition(effectiveRoutePosition, filterTmpPositionList, routeNameTmp, routeId);
                //重置routeName
                routeNameTmp = routePositionMap.get("routeName").toString();
                routeId = (Integer) routePositionMap.get("routeId");
                //清空前一个站点的所有定位
                filterTmpPositionList = new ArrayList<>();
            }
            routePositionMap.remove("routeName");
            routePositionMap.remove("routeId");
            //然后将本定位信息存入临时站点list
            filterTmpPositionList.add(routePositionMap);

//                }
        }
        //如果全部站定位数据都不符合时间段，则时间返回空list
        if (0 == allRoutePosition.size()) {
            return ResultWrapperUtil.setSuccessOf(allRoutePosition);
        }
        //保存最后一组记录
        addToEffectiveRoutePosition(effectiveRoutePosition, filterTmpPositionList, routeNameTmp, routeId);
        //将最后的有效路线定位存入结果对象中
        resultWrapper = ResultWrapperUtil.setSuccessOf(effectiveRoutePosition);
        return resultWrapper;
    }

    /**
     * 将路线名、id和轨迹信息存入临时map中，并加入到有效路线轨迹list
     *
     * @param effectiveRoutePosition 待进的有效路线轨迹list
     * @param filterTmpPositionList  已过滤的该路线的轨迹记录list
     * @param routeNameTmp           路线名
     * @param routeId                路线id
     */
    private void addToEffectiveRoutePosition(List<Map<String, Object>> effectiveRoutePosition, List<Map<String, Object>> filterTmpPositionList, String routeNameTmp, Integer routeId) {
        //创建存放站点的所有站点和站点名Map
        Map<String, Object> filterTmpRouteMap = new HashMap<>(16);
        //将站点名记录保存
        filterTmpRouteMap.put("routeName", routeNameTmp);
        filterTmpRouteMap.put("routeId", routeId);
        //将该站点的所有定位信息保存下来
        filterTmpRouteMap.put("trackRoute", filterTmpPositionList);
        //将该map存入总的list中
        effectiveRoutePosition.add(filterTmpRouteMap);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findAllEffectiveRoutePositionByRouteId(Integer routeId, Integer startIndex) {
        ResultWrapper resultWrapper;
        //查询总记录数
        Integer count = sbBusPositionDao.findCountByRouteId(JudgeTimeUtil.getWeek(), routeId);
        //如果无定位记录，则直接返回一个map，无需再查数据库
        if (0 == count || startIndex >= count) {
            //创建待返回的有效list集合，含定位和routeId
            Map<String, Object> mapTmp = new HashMap<>(16);
            //存放routeId
            mapTmp.put("routeId", routeId);
            //存入定位信息
            mapTmp.put("trackRoute", new ArrayList<>());
            return ResultWrapperUtil.setSuccessOf(mapTmp);
        }
        //减去已获得的记录数
        count -= startIndex;
        //查询该路线id从startIndex到count的记录
        List<Map<String, Object>> routePositionList = sbBusPositionDao.findAllRoutePositionByRouteId(JudgeTimeUtil.getWeek(), routeId, startIndex, count);
        //遍历查询到的记录，进行一定的处理
        for (Map<String, Object> routePositionMap : routePositionList) {
            //TODO 根据时间段过滤
            //如果在该时间段，删除不需要的数据
            routePositionMap.remove("startTime");
            routePositionMap.remove("endTime");
            routePositionMap.remove("recodeTime");

        }
        //创建待返回的有效list集合，含定位和routeId
        Map<String, Object> resultMap = new HashMap<>(16);
        //存放routeId
        resultMap.put("routeId", routeId);
        //存入定位信息
        resultMap.put("trackRoute", routePositionList);
        resultWrapper = ResultWrapperUtil.setSuccessOf(resultMap);
        return resultWrapper;
    }

    private void optimizeTmp(List<Map<String, Object>> optimizeList) {
        if (optimizeList.size() >= 40) {
            int removeNumber = 20;
            for (int index = 0; index <= removeNumber; index++) {
                optimizeList.remove(index);
                index--;
                removeNumber--;
            }
        }
    }
}
