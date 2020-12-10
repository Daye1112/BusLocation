package com.ky.gps.service.impl;

import com.ky.gps.dao.SbBusRouteDao;
import com.ky.gps.entity.SbBusRoute;
import com.ky.gps.service.SbBusRouteService;
import com.ky.gps.util.JudgeTimeUtil;
import com.ky.gps.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 校车与路线对应联系的Service
 */
@Service
public class SbBusRouteServiceImpl implements SbBusRouteService {

    @Resource
    private SbBusRouteDao sbBusRouteDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> update(SbBusRoute sbBusRoute) {
        sbBusRouteDao.update(sbBusRoute);
        return sbBusRouteDao.findById(sbBusRoute.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> save(SbBusRoute sbBusRoute) {
        sbBusRouteDao.save(sbBusRoute);
        return sbBusRouteDao.findById(sbBusRoute.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateValidById(Integer id) {
        sbBusRouteDao.updateValidById(id, 0);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public List<Map<String, Object>> findByRouteId(Integer routeId, String week, String startTime, String endTime) {
        List<Map<String, Object>> busRouteList = sbBusRouteDao.findByRouteId(routeId, week);
        if (StringUtil.isNotEmpty(startTime) && StringUtil.isNotEmpty(endTime)) {
            //遍历筛选校车路线信息
            for (int i = 0; i < busRouteList.size(); i++) {
                String sbbrStartTime = busRouteList.get(i).get("sbbrStartTime").toString();
                String sbbrEndTime = busRouteList.get(i).get("sbbrEndTime").toString();
                //判断路线时间是否在筛选时间段内
                if (!JudgeTimeUtil.isTimeEffectiveDate(sbbrStartTime, startTime, endTime)
                        || !JudgeTimeUtil.isTimeEffectiveDate(sbbrEndTime, startTime, endTime)) {
                    busRouteList.remove(i--);
                }
            }
        }
        return busRouteList;
    }

    @Deprecated
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public List<SbBusRoute> findAllRelation() {
        List<SbBusRoute> allRelation = sbBusRouteDao.findAllRelation();
        //TODO 对记录进行筛选，判断是否在可查询时间段
        return allRelation;
    }
}
