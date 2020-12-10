package com.ky.gps.service.impl;

import com.ky.gps.dao.SbRouteDao;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SbRoute;
import com.ky.gps.service.SbRouteService;
import com.ky.gps.util.JudgeTimeUtil;
import com.ky.gps.util.ResultWrapperUtil;
import com.ky.gps.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 路线实体类的Service接口实现类
 */
@Service
public class SbRouteServiceImpl implements SbRouteService {

    @Resource
    private SbRouteDao sbRouteDao;

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public List<Map<String, Object>> findByNameAndTimeFuzzy(String sbrRouteName, String startTime, String endTime) {
        List<Map<String, Object>> routeList = sbRouteDao.findByNameFuzzy("%" + sbrRouteName + "%");
        if (routeList == null) {
            return null;
        }
        if (StringUtil.isNotEmpty(startTime) && StringUtil.isNotEmpty(endTime)) {
            for (int i = 0; i < routeList.size(); i++) {
                if (!JudgeTimeUtil.isTimeEffectiveDate(routeList.get(i).get("sbrDepartTime").toString(), startTime, endTime)) {
                    routeList.remove(i);
                    i--;
                }
            }
        }
        return routeList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> save(SbRoute sbRoute) {
        sbRouteDao.save(sbRoute);
        return sbRouteDao.findById(sbRoute.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        sbRouteDao.updateValidById(id, 0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> updateById(SbRoute sbRoute) {
        sbRouteDao.updateById(sbRoute);
        return sbRouteDao.findById(sbRoute.getId());
    }

    //    @Cacheable(value = "route_base_info", key = "#id")
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public SbRoute findBaseInfoById(Integer id) {
        return sbRouteDao.findBaseInfoById(id);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public String findNameById(Integer id) {
        return sbRouteDao.findNameById(id);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public List<Map<String, Object>> findAllBaseInfo() {
        return sbRouteDao.findAllBaseInfo();
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findAllIdAndName() {
        //创建待返回的结果对象
        ResultWrapper resultWrapper = null;
        //接收查询的结果集合
        List<Map<String, Object>> sbRouteList = sbRouteDao.findAllIdAndName();
        //将结果集合存入结果对象中
        resultWrapper = ResultWrapperUtil.setSuccessOf(sbRouteList);
        return resultWrapper;
    }
}
