package modenlibrary.scheduled.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.utils.RedisUtil;
import modenlibrary.anno.Operation;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author L.star
 * @date 2021/5/17 17:41
 */
@Component
@Slf4j
@Operation("清除上月借阅人数")
public class clearLastLendBookNumTask extends QuartzJobBean {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("清除日期 {} 的借书人数", DateUtil.lastMonth().monthBaseOne());
        redisUtil.del("LendBookNum"+DateUtil.lastMonth().monthBaseOne());
    }
}
