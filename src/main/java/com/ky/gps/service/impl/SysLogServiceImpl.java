package com.ky.gps.service.impl;

import com.ky.gps.dao.SysLogDao;
import com.ky.gps.entity.SysLog;
import com.ky.gps.service.SysLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Daye
 * 用户操作记录Service接口实现类
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogDao sysLogDao;

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    @Override
    public List<Map<String, Object>> findByUserAndNameAndOperatorAndCreatedDate(String userNumber, String name, String operator, String createdDate) {
        return sysLogDao.findByUserAndNameAndOperatorAndCreatedDate(userNumber, name, operator, createdDate);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSysLog(SysLog sysLog) {
        sysLogDao.saveSysLog(sysLog);
    }
}
