package com.ky.gps.service.impl;

import com.ky.gps.dao.SbUserRoleDao;
import com.ky.gps.dao.SysRoleDao;
import com.ky.gps.dao.SysUserDao;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SbUserRole;
import com.ky.gps.service.SbUserRoleService;
import com.ky.gps.util.ResultWrapperUtil;
import com.ky.gps.util.SbUserRoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * <p>
 * 用户和角色表多对多关系的Service接口的实现类
 */
@Service
public class SbUserRoleServiceImpl implements SbUserRoleService {

    private SbUserRoleDao sbUserRoleDao;
    private SysUserDao sysUserDao;
    private SysRoleDao sysRoleDao;

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper fuzzyQueryByWorkIdAndRealNameAndDepartmentId(Map<String, Object> params) {
        List<Map<String, Object>> userRoleList = sbUserRoleDao.fuzzyQueryByWorkIdAndRealNameAndDepartmentId(params);
        return ResultWrapperUtil.setSuccessOf(userRoleList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultWrapper updateUserRoleByUserId(Integer id, List<Integer> needDeleteIdList, List<Integer> needAddIdList) {
        if (needAddIdList != null && needAddIdList.size() > 0) {
            sbUserRoleDao.batchSaveByUserId(id, needAddIdList);
        }
        if (needDeleteIdList != null && needDeleteIdList.size() > 0) {
            sbUserRoleDao.batchUpdateValidByUserId(id, needDeleteIdList, 0);
        }
        return ResultWrapperUtil.setSuccessOf(null);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findRoleIdByUserId(Integer userId) {
        List<Integer> roleIdList = sbUserRoleDao.findRoleIdByUserId(userId);
        return ResultWrapperUtil.setSuccessOf(roleIdList);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findUserRolesStatusByUserId(Integer userId) {
        ResultWrapper resultWrapper;
        //查询用户拥有的角色id集合
        List<Integer> userRoleIdList = sbUserRoleDao.findRoleIdByUserId(userId);
        //查询所有角色集合
        List<Map<String, Object>> roleList = sysRoleDao.findIdAndSrName();
        //如果无角色，直接返回null
        if (roleList == null) {
            resultWrapper = ResultWrapperUtil.setSuccessOf(null);
        } else {
            //判断用户是否拥有角色
            List<Map<String, Object>> roleListRes = SbUserRoleUtil.checkUserHasRole(userRoleIdList, roleList);
            resultWrapper = ResultWrapperUtil.setSuccessOf(roleListRes);
        }
        return resultWrapper;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findAllUserAndRole() {
        List<Map<String, Object>> allUserAndRole = sbUserRoleDao.findAllUserAndRole();
        return ResultWrapperUtil.setSuccessOf(allUserAndRole);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public List<String> findSysRoleSrSourceBySysUserId(Integer id) {
        return sbUserRoleDao.findSysRoleSrSourceBySysUserId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultWrapper saveUserRole(SbUserRole sbUserRole) {
        sbUserRoleDao.saveUserRole(sbUserRole);
        return ResultWrapperUtil.setSuccessOf(null);
    }

    @Autowired
    public void setSbUserRoleDao(SbUserRoleDao sbUserRoleDao) {
        this.sbUserRoleDao = sbUserRoleDao;
    }

    @Autowired
    public void setSysUserDao(SysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
    }

    @Autowired
    public void setSysRoleDao(SysRoleDao sysRoleDao) {
        this.sysRoleDao = sysRoleDao;
    }
}
