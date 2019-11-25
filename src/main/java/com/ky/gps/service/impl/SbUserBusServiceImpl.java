package com.ky.gps.service.impl;

import com.ky.gps.dao.SbUserBusDao;
import com.ky.gps.service.SbUserBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 用户路线中间表service-实现类
 * @author Darren
 */
@Service
public class SbUserBusServiceImpl implements SbUserBusService {

    @Autowired
    private SbUserBusDao sbUserBusDao;

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public List<Map<String, Object>> findRouteIdAndRouteNameByUserId(Integer userId) {
        return sbUserBusDao.findRouteIdAndRouteNameByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Map<String, Object>> list(Map<String, Object> params) {
        return sbUserBusDao.list(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Integer userId, List<Integer> routeIds) {
        sbUserBusDao.deleteByUserId(userId);
        sbUserBusDao.insert(userId, routeIds);
    }
}
