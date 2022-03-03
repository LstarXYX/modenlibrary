package modenlibrary.service;

import modenlibrary.entity.QuartzBean;

import java.util.List;

/**
 * @author L.star
 * @date 2021/5/17 12:52
 */
public interface SchedulerService {

    public void createJob(QuartzBean quartzBean);

    public void pauseJob(QuartzBean quartzBean);

    public void resumeJob(QuartzBean quartzBean);

    public boolean runOnce(QuartzBean quartzBean);

    public void updateJob(QuartzBean quartzBean);

    public void delJob(QuartzBean quartzBean);

    List<QuartzBean> getJobs();
}
