package com.ky.gps.timer;

import com.ky.gps.service.SbBusPositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 每天23点迁移busPosition表中的记录到busPositionHis表中
 *
 * @author Daye
 */
@Component
public class MovePositionTimer {
    /**
     * 日志打印对象
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(MovePositionTimer.class);

    @Resource
    private SbBusPositionService sbBusPositionService;


    @Scheduled(cron = "0 0 23 * * ?")
    public void moveTable() {
        LOGGER.info("准备执行数据库表的迁移工作");
        sbBusPositionService.deletePositionAndMoveToHis();
    }
}
