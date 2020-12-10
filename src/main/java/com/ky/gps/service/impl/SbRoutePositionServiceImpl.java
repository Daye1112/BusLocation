package com.ky.gps.service.impl;

import com.ky.gps.dao.SbRoutePositionDao;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.service.SbRoutePositionService;
import com.ky.gps.util.ResultWrapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * <p>
 * 路线行驶轨迹Service服务层接口实现类
 */
@Service
public class SbRoutePositionServiceImpl implements SbRoutePositionService {

    @Resource
    private SbRoutePositionDao sbRoutePositionDao;

    //    @Cacheable(value = "station_lon_lat", key = "#routeId")
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findLonAndLatByRouteId(String routeId) {
        List<Map<String, Object>> lonAndLatList = sbRoutePositionDao.findLonAndLatByRouteId(Integer.parseInt(routeId));
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("routePosition", lonAndLatList);
        resultMap.put("routeId", routeId);
        return ResultWrapperUtil.setSuccessOf(resultMap);
    }
}
