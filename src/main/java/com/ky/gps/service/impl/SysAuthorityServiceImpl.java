package com.ky.gps.service.impl;

import com.ky.gps.dao.SysAuthorityDao;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SysAuthority;
import com.ky.gps.entity.SysAuthorityExtractAttribute;
import com.ky.gps.service.SysAuthorityService;
import com.ky.gps.util.ResultWrapperUtil;
import com.ky.gps.util.SysAuthorityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 权限service层-实现类
 *
 * @author Daye
 */
@Service
public class SysAuthorityServiceImpl implements SysAuthorityService {

    private SysAuthorityDao sysAuthorityDao;

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findAll() {
        List<Map<String, Object>> allAuthority = sysAuthorityDao.findAll();
        Map<String, List<SysAuthorityExtractAttribute>> listMapToJsonObj =
                SysAuthorityUtil.findListMapToJsonObj(allAuthority);
        return ResultWrapperUtil.setSuccessOf(listMapToJsonObj);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public List<String> findAllSaName() {
        return sysAuthorityDao.findAllSaName();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultWrapper insertSelective(SysAuthority sysAuthority) {
        sysAuthorityDao.insertSelective(sysAuthority);
        Map<String, Object> authorityMap = sysAuthorityDao.findById(sysAuthority.getId());
        return ResultWrapperUtil.setSuccessOf(authorityMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultWrapper updateByPrimaryKeySelective(SysAuthority sysAuthority) {
        sysAuthorityDao.updateByPrimaryKeySelective(sysAuthority);
        Map<String, Object> authorityMap = sysAuthorityDao.findById(sysAuthority.getId());
        return ResultWrapperUtil.setSuccessOf(authorityMap);
    }

    @Autowired
    public void setSysAuthorityDao(SysAuthorityDao sysAuthorityDao) {
        this.sysAuthorityDao = sysAuthorityDao;
    }
}
