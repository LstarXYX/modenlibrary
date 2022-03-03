package modenlibrary.Common.init;

import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.utils.QuartzUtil;
import modenlibrary.entity.QuartzBean;
import modenlibrary.mapper.QuartzJobsMapper;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author L.star
 * @date 2021/5/18 8:57
 */
@Component
@Order(10)
@Slf4j
public class QuartzInit implements ApplicationRunner {

    @Autowired
    private QuartzJobsMapper quartzJobsMapper;
    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Quartz初始化");
        List<QuartzBean> list = quartzJobsMapper.queryAll(null);
        if (list.size() != 0) {
            log.info("运行原本配置的任务");
            for (QuartzBean quartzBean : list) {
                QuartzUtil.createJob(scheduler,quartzBean);
                //1运行 0暂停
                if (quartzBean.getStatus().equals(0)) {
                    QuartzUtil.pauseJob(scheduler,quartzBean.getJobName());
                }
            }
        }
        log.info("Quartz初始化完成");
    }
}
