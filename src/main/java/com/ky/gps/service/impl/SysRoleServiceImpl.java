package com.ky.gps.service.impl;

import com.ky.gps.dao.SbRoleAuthorityDao;
import com.ky.gps.dao.SbUserRoleDao;
import com.ky.gps.dao.SysRoleDao;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SysRole;
import com.ky.gps.service.SysRoleService;
import com.ky.gps.util.ResultWrapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * <p>
 * 角色表Service层接口的实现类
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    private SysRoleDao sysRoleDao;
    private SbUserRoleDao sbUserRoleDao;
    private SbRoleAuthorityDao sbRoleAuthorityDao;

    @Override
    public ResultWrapper findByCreatedDate(Date startDate, Date endDate) {
        List<Map<String, Object>> roleList = sysRoleDao.findByCreatedDate(startDate, endDate);
        return ResultWrapperUtil.setSuccessOf(roleList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultWrapper updateById(SysRole sysRole) {
        sysRoleDao.updateById(sysRole);
        Map<String, Object> roleMap = sysRoleDao.findById(sysRole.getId());
        return ResultWrapperUtil.setSuccessOf(roleMap);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findById(Integer id) {
        Map<String, Object> sysRole = sysRoleDao.findById(id);
        return ResultWrapperUtil.setSuccessOf(sysRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultWrapper saveRole(SysRole sysRole) {
        sysRoleDao.saveRole(sysRole);
        Map<String, Object> sysRoleMap = sysRoleDao.findById(sysRole.getId());
        return ResultWrapperUtil.setSuccessOf(sysRoleMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultWrapper rollbackById(Integer id) {
        sbUserRoleDao.updateValidByRoleId(id, 1);
        sbRoleAuthorityDao.updateValidByRoleId(id, 1);
        sysRoleDao.updateValidById(id, 1);
        return ResultWrapperUtil.setSuccessOf(null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultWrapper deleteById(Integer id) {
        sbUserRoleDao.updateValidByRoleId(id, 0);
        sbRoleAuthorityDao.updateValidByRoleId(id, 0);
        sysRoleDao.updateValidById(id, 0);
        return ResultWrapperUtil.setSuccessOf(null);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findAllRole() {
        return ResultWrapperUtil.setSuccessOf(sysRoleDao.findAllRole());
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findNameById(Integer roleId) {
        //获取该id的角色name
        String roleName = sysRoleDao.findNameById(roleId);
        //将name封装进json对象中返回
        return ResultWrapperUtil.setSuccessOf(roleName);
    }

    @Autowired
    public void setSysRoleDao(SysRoleDao sysRoleDao) {
        this.sysRoleDao = sysRoleDao;
    }

    @Autowired
    public void setSbUserRoleDao(SbUserRoleDao sbUserRoleDao) {
        this.sbUserRoleDao = sbUserRoleDao;
    }

    @Autowired
    public void setSbRoleAuthorityDao(SbRoleAuthorityDao sbRoleAuthorityDao) {
        this.sbRoleAuthorityDao = sbRoleAuthorityDao;
    }
}
