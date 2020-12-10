package com.ky.gps.service;

import com.ky.gps.entity.ResultWrapper;
import com.ky.gps.entity.SysUser;

import java.util.Map;

/**
 * @author Daye
 * 系统用户Service接口
 */
public interface SysUserService {

    /**
     * 根据真实姓名，工号和部门id模糊查询
     *
     * @param params 存放参数的map
     *               realName        真实姓名
     *               workId          工号
     *               departmentId    部门id
     * @return 返回key={id,departmentName,workId,realName,idCard,phone,email}
     */
    ResultWrapper findBaseInfoLikeRealNameAndWorkIdAndDepartment(Map<String, Object> params);

    /**
     * 根据用户教工号来查询普通用户信息
     *
     * @param workId 教工号
     * @return 返回查询结果并封装进map中
     */
    Map<String, Object> findBaseInfoByWorkId(String workId);

    /**
     * 根据用户教工号来查询管理员信息
     *
     * @param workId 教工号
     * @return 返回查询结果封装进map中
     */
    Map<String, Object> findAdminBaseInfoByWordId(String workId);

    /**
     * 根据用户id查询用户基本信息
     *
     * @param userId 用户id
     * @return 返回json格式
     */
    ResultWrapper findUserBaseInfoById(Integer userId);

    /**
     * 根据页大小返回总页数
     *
     * @param pageSize 页大小
     * @return 返回json格式数据
     */
    ResultWrapper findTotalPages(Integer pageSize);

    /**
     * 根据depId进行查询，获取总记录数
     *
     * @param depId    部门id
     * @param pageSize 页大小
     * @return Json对象
     */
    ResultWrapper findTotalByDepartmentId(Integer depId, Integer pageSize);

    /**
     * 根据depId进行查询，并进行分页
     *
     * @param depId      部门id
     * @param startIndex 查询记录索引
     * @param pageSize   页大小
     * @return keys={id, departmentName, workId, realName, idCode, phone, email}
     */
    ResultWrapper findUserByDepartmentId(Integer depId, Integer startIndex, Integer pageSize);


    /**
     * 根据realName进行模糊查询，返回记录总页数和总记录数
     *
     * @param realName 真实姓名
     * @param pageSize 页大小
     * @return Json对象
     */
    ResultWrapper findTotalByRealNameFuzzy(String realName, Integer pageSize);

    /**
     * 根据realName进行模糊查询，并进行分页
     *
     * @param realName   姓名
     * @param startIndex 查询记录索引
     * @param pageSize   页大小
     * @return keys={id, departmentName, workId, realName, idCode, phone, email}
     */
    ResultWrapper findUserByRealNameFuzzyPages(String realName, Integer startIndex, Integer pageSize);

    /**
     * 根据workId进行模糊查询，获取总记录数
     *
     * @param wordId   工号
     * @param pageSize 页大小
     * @return Json对象
     */
    ResultWrapper findTotalByWorkIdFuzzyPages(String wordId, Integer pageSize);

    /**
     * 根据workId进行模糊查询，并进行分页
     *
     * @param wordId     工号
     * @param startIndex 查询记录索引
     * @param pageSize   页大小
     * @return keys={id, departmentName, workId, realName, idCode, phone, email}
     */
    ResultWrapper findUserByWorkIdFuzzyPages(String wordId, Integer startIndex, Integer pageSize);

    /**
     * 根据用户账号/教工号、密码来查询用户信息
     * 因为登录时需要做一些特殊处理，因此这里只返回map，由controller来进行处理
     *
     * @param map workId:教工号;password:密码
     * @return 返回查询结果并封装进map中
     */
    Map<String, Object> simpleUserLogin(Map<String, Object> map);

    /**
     * 根据用户教工号、密码来查询管理员信息
     *
     * @param map workId:教工号;password:密码
     * @return 返回查询结果封装进map中
     */
    Map<String, Object> adminUserLogin(Map<String, Object> map);

    /**
     * 获取所有用户的信息集合
     *
     * @return 将集合封装到json对象中返回
     */
    ResultWrapper findUserList();

    /**
     * 分页查询用户信息
     *
     * @param startIndex 起始查询位置
     * @param pageSize   查询条数
     * @return keys={id, departmentName, workId, realName, idCode, phone, email}
     */
    ResultWrapper findUserByPages(Integer startIndex, Integer pageSize);

    /**
     * 添加用户的基本信息
     *
     * @param sysUser 包含待添加的用户基本信息和用户所对应的部门id
     * @return 返回刚添加的用户id
     */
    ResultWrapper saveUserBaseInfo(SysUser sysUser);

    /**
     * 根据用户id删除用户
     * 不是真删除，而是将Valid设为0，即无效
     *
     * @param userId   用户id
     * @param updateBy 更新者wordId
     * @return json对象
     */
    ResultWrapper deleteUserByUserId(Integer userId, String updateBy);

    /**
     * 根据用户id查询用户的真实姓名
     *
     * @param id 用户id
     * @return json对象
     */
    ResultWrapper findRealNameById(Integer id);

    /**
     * 更新用户基本信息
     *
     * @param sysUser 待更新的用户对象
     * @return json对象
     */
    Map<String, Object> updateUserBaseInfo(SysUser sysUser);

    /**
     * 根据用户id查询该id的所有基本信息
     * keys={id, departmentName, workId, realName, idCode, phone, email}
     *
     * @param userId 用户id
     * @return json对象
     */
    ResultWrapper findSelfBaseInfoByUserId(Integer userId);

    /**
     * 根据用户id和密码查询用户的真实姓名
     *
     * @param password 密码
     * @param userId   用户id
     * @return 返回realName
     */
    ResultWrapper findRealNameByPasswordAndUserId(String password, Integer userId);

    /**
     * 根据id更新用户的密码和加密的密码
     * password和salt
     *
     * @param sysUser 待更新用户对象
     * @return json对象，data为null
     */
    ResultWrapper updatePassword(SysUser sysUser);

    /**
     * 验证邮箱是否存在
     *
     * @param email 邮箱
     * @return true:存在，false:不存在
     */
    Boolean isEffectiveEmail(String email);

    /**
     * 根据email查询用户基本信息
     *
     * @param email 邮箱
     * @return 用户信息map, keys={sysUserId, realName, workId, departmentName}
     */
    Map<String, Object> findBaseInfoByEmail(String email);

    /**
     * 根据邮箱来修改密码
     *
     * @param sysUser 存放email、password、salt、lastPsdDate和updatedBy数据的对象
     * @return 返回json格式数据
     */
    ResultWrapper modifyPasswordByEmail(SysUser sysUser);
}
