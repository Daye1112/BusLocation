package com.ky.gps.service.impl;

import com.ky.gps.dao.SbUserBusDao;
import com.ky.gps.dao.SbUserRoleDao;
import com.ky.gps.dao.SysUserDao;
import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SysUser;
import com.ky.gps.service.SysUserService;
import com.ky.gps.util.MapUtil;
import com.ky.gps.util.ResultWrapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * SysUserService接口的实现类，业务逻辑处理
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SbUserRoleDao sbUserRoleDao;
    @Resource
    private SbUserBusDao sbUserBusDao;

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findBaseInfoLikeRealNameAndWorkIdAndDepartment(Map<String, Object> params) {
        List<Map<String, Object>> userList = sysUserDao.findBaseInfoLikeRealNameAndWorkIdAndDepartment(params);
        return ResultWrapperUtil.setSuccessOf(userList);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public Map<String, Object> findBaseInfoByWorkId(String workId) {
        return sysUserDao.findBaseInfoByWorkId(workId);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public Map<String, Object> findAdminBaseInfoByWordId(String workId) {
        return sysUserDao.findAdminBaseInfoByWordId(workId);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findUserBaseInfoById(Integer userId) {
        Map<String, Object> user = sysUserDao.findUserBaseInfoById(userId);
        return ResultWrapperUtil.setSuccessOf(user);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findTotalPages(Integer pageSize) {
        //获取总记录数
        int totalRecoding = sysUserDao.findTotalRecoding();
        //根据总记录数计算得到总页数
        int totalPages = (totalRecoding + pageSize - 1) / pageSize;
        return MapUtil.setTotalInfoIntoMap(totalRecoding, totalPages);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findTotalByDepartmentId(Integer depId, Integer pageSize) {
        //获取总记录数
        int totalRecoding = sysUserDao.findTotalByDepartmentId(depId);
        //根据总记录数计算得到总页数
        int totalPages = (totalRecoding + pageSize - 1) / pageSize;
        return MapUtil.setTotalInfoIntoMap(totalRecoding, totalPages);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findUserByDepartmentId(Integer depId, Integer startIndex, Integer pageSize) {
        List<Map<String, Object>> userList = sysUserDao.findUserByDepartmentId(depId, startIndex, pageSize);
        return ResultWrapperUtil.setSuccessOf(userList);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findTotalByRealNameFuzzy(String realName, Integer pageSize) {
        //获取总记录数
        int totalRecoding = sysUserDao.findTotalByRealNameFuzzy("%" + realName + "%");
        //根据总记录数计算得到总页数
        int totalPages = (totalRecoding + pageSize - 1) / pageSize;
        return MapUtil.setTotalInfoIntoMap(totalRecoding, totalPages);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findUserByRealNameFuzzyPages(String realName, Integer startIndex, Integer pageSize) {
        List<Map<String, Object>> userList = sysUserDao.findUserByRealNameFuzzyPages("%" + realName + "%", startIndex, pageSize);
        return ResultWrapperUtil.setSuccessOf(userList);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findTotalByWorkIdFuzzyPages(String wordId, Integer pageSize) {
        //获取总记录数
        int totalRecoding = sysUserDao.findTotalByWorkIdFuzzyPages("%" + wordId + "%");
        //根据总记录数计算得到总页数
        int totalPages = (totalRecoding + pageSize - 1) / pageSize;
        return MapUtil.setTotalInfoIntoMap(totalRecoding, totalPages);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findUserByWorkIdFuzzyPages(String wordId, Integer startIndex, Integer pageSize) {
        List<Map<String, Object>> userList = sysUserDao.findUserByWorkIdFuzzyPages("%" + wordId + "%", startIndex, pageSize);
        return ResultWrapperUtil.setSuccessOf(userList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultWrapper modifyPasswordByEmail(SysUser sysUser) {
        sysUserDao.updatePasswordByEmail(sysUser);
        return ResultWrapperUtil.setSuccessOf(null);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public Map<String, Object> findBaseInfoByEmail(String email) {
        return sysUserDao.findBaseInfoByEmail(email);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public Boolean isEffectiveEmail(String email) {
        //使用email进行查询，如果存在，则返回email
        Integer id = sysUserDao.findIdByEmail(email);
        //如果id存在返回true，反之false
        return id != null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultWrapper updatePassword(SysUser sysUser) {
        sysUserDao.updatePassword(sysUser);
        return ResultWrapperUtil.setSuccessOf(null);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findRealNameByPasswordAndUserId(String password, Integer userId) {
        String realName = sysUserDao.findRealNameByPasswordAndUserId(password, userId);
        return ResultWrapperUtil.setSuccessOf(realName);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findSelfBaseInfoByUserId(Integer userId) {
        List<Map<String, Object>> selfBaseInfo = sysUserDao.findSelfBaseInfoByUserId(userId);
        return ResultWrapperUtil.setSuccessOf(selfBaseInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> updateUserBaseInfo(SysUser sysUser) {
        if (!StringUtils.isEmpty(sysUser.getEmail())) {
            Integer exists = sysUserDao.findIdByEmail(sysUser.getEmail());
            if (exists != null && !exists.equals(sysUser.getId())) {
                return null;
            }
        }
        //更新用户信息
        sysUserDao.updateUserBaseInfo(sysUser);
        return sysUserDao.findUserBaseInfoById(sysUser.getId());
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findRealNameById(Integer id) {
        //查询真实姓名，不存在则为null
        String realName = sysUserDao.findRealNameById(id);
        //封装realName返回json对象
        return ResultWrapperUtil.setSuccessOf(realName);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultWrapper deleteUserByUserId(Integer userId, String updateBy) {
        //删除用户和校车的对应关系
        sbUserBusDao.deleteUserBusByUserId(userId, updateBy);
        //删除用户和角色的对应关系
        sbUserRoleDao.deleteUserRoleByUserId(userId, updateBy);
        //删除用户
        sysUserDao.deleteUserByUserId(userId, updateBy);
        //返回json对象
        return ResultWrapperUtil.setSuccessOf(null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultWrapper saveUserBaseInfo(SysUser sysUser) {
        if (!StringUtils.isEmpty(sysUser.getEmail()) && sysUserDao.findIdByEmail(sysUser.getEmail()) != null) {
            return null;
        }
        //添加用户信息
        sysUserDao.saveUserBaseInfo(sysUser);
        Map<String, Object> user = sysUserDao.findUserBaseInfoById(sysUser.getId());
        //将添加完成后该用户的id返回
        return ResultWrapperUtil.setSuccessOf(user);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findUserByPages(Integer startIndex, Integer pageSize) {
        //获取所有用户的信息集合
        List<Map<String, Object>> userList = sysUserDao.findUserByPages(startIndex, pageSize);
        //返回json对象
        return ResultWrapperUtil.setSuccessOf(userList);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public ResultWrapper findUserList() {
        //获取所有用户信息集合
        List<Map<String, Object>> userList = sysUserDao.findAllUser();
        //返回json对象
        return ResultWrapperUtil.setSuccessOf(userList);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public Map<String, Object> adminUserLogin(Map<String, Object> map) {
        return sysUserDao.findAdminBaseInfoByWordIdAndPassword(map);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public Map<String, Object> simpleUserLogin(Map<String, Object> map) {
        return sysUserDao.findBaseInfoByWorkIdAndPassword(map);
    }
}
