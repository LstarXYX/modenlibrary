package modenlibrary.scheduled.task;

import lombok.extern.slf4j.Slf4j;
import modenlibrary.anno.Operation;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author L.star
 * @date 2021/5/17 16:28
 */
@Component
@Slf4j
@Operation("测试任务")
public class testTask extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("测试任务");
    }
}
