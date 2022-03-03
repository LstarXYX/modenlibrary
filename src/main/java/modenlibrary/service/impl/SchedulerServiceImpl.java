package modenlibrary.service.impl;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.Common.utils.QuartzUtil;
import modenlibrary.entity.QuartzBean;
import modenlibrary.mapper.QuartzJobsMapper;
import modenlibrary.service.SchedulerService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author L.star
 * @date 2021/5/17 12:55
 */
@Slf4j
@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private Scheduler scheduler;
    @Resource
    private QuartzJobsMapper quartzJobsMapper;

    @Async("asyncServiceExecutor")
    @Override
    public void createJob(QuartzBean quartzBean) {
        log.info("创建新的定时任务 【{}】",quartzBean);
        try {
            QuartzUtil.createJob(scheduler, quartzBean);
            quartzJobsMapper.insert(quartzBean);
        } catch (Exception e) {
            log.error("错误：创建任务【{}】失败，原因是：{}",quartzBean,e.getMessage());
            throw new BusinessException(ReturnCode.SCHEDULER_ERROR);
        }
        log.info("定时任务创建成功");
    }

    @Async("asyncServiceExecutor")
    @Override
    public void pauseJob(QuartzBean quartzBean) {
        if (StrUtil.isBlank(quartzBean.getJobName())) {
            throw new BusinessException(ReturnCode.FORM_ERROR);
        }
        log.info("暂停定时任务 【{}】",quartzBean);
        try {
            QuartzUtil.pauseJob(scheduler, quartzBean.getJobName());
            quartzJobsMapper.update(quartzBean);
        } catch (Exception e) {
            log.error("错误：暂停定时任务【{}】失败，原因是：{}",quartzBean,e.getMessage());
            throw new BusinessException(ReturnCode.SCHEDULER_ERROR);
        }
        log.info("暂停定时任务完成");
    }

    @Async("asyncServiceExecutor")
    @Override
    public void resumeJob(QuartzBean quartzBean) {
        if (StrUtil.isBlank(quartzBean.getJobName())) {
            throw new BusinessException(ReturnCode.FORM_ERROR);
        }
        log.info("恢复定时任务 【{}】",quartzBean);
        try {
            QuartzUtil.resumeJob(scheduler, quartzBean.getJobName());
            quartzJobsMapper.update(quartzBean);
        } catch (Exception e) {
            log.error("错误：恢复定时任务【{}】失败，原因是：{}",quartzBean,e.getMessage());
            throw new BusinessException(ReturnCode.SCHEDULER_ERROR);
        }
        log.info("恢复定时任务完成");
    }

    @Override
    public boolean runOnce(QuartzBean quartzBean) {
        if (StrUtil.isBlank(quartzBean.getJobName())) {
            throw new BusinessException(ReturnCode.FORM_ERROR);
        }
        log.info("运行一次定时任务 【{}】",quartzBean);
        try {
            QuartzUtil.runOnce(scheduler, quartzBean.getJobName());
        } catch (Exception e) {
            log.error("错误：运行定时任务【{}】失败，原因是：{}",quartzBean,e.getMessage());
            throw new BusinessException(ReturnCode.SCHEDULER_ERROR);
        }
        log.info("运行定时任务完成");
        return true;
    }

    @Async("asyncServiceExecutor")
    @Override
    public void updateJob(QuartzBean quartzBean) {
        if (ObjectUtil.isNull(quartzBean)) {
            throw new BusinessException(ReturnCode.FORM_ERROR);
        }
        log.info("更新定时任务 【{}】",quartzBean);
        try {
            QuartzUtil.updateJob(scheduler,quartzBean);
            quartzJobsMapper.update(quartzBean);
        } catch (Exception e) {
            log.error("错误：更新定时任务【{}】失败，原因是：{}",quartzBean,e.getMessage());
            throw new BusinessException(ReturnCode.SCHEDULER_ERROR);
        }
        log.info("更新定时任务完成");
    }

    @Async("asyncServiceExecutor")
    @Override
    public void delJob(QuartzBean quartzBean) {
        if (StrUtil.isBlank(quartzBean.getJobName())) {
            throw new BusinessException(ReturnCode.FORM_ERROR);
        }
        log.info("删除定时任务 【{}】",quartzBean);
        try {
            QuartzUtil.delJob(scheduler, quartzBean.getJobName());
            quartzJobsMapper.deleteById(quartzBean.getId());
        } catch (Exception e) {
            log.error("错误：删除定时任务【{}】失败，原因是：{}",quartzBean,e.getMessage());
            throw new BusinessException(ReturnCode.SCHEDULER_ERROR);
        }
        log.info("删除定时任务完成");
    }

    @Override
    public List<QuartzBean> getJobs() {
        log.info("查询定时任务列表");
        List<QuartzBean> list = quartzJobsMapper.queryAll(null);
        log.info("查询到的记录条数：{}",list.size());
        return list;
    }
}
