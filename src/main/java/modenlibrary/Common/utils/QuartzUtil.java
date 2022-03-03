package modenlibrary.Common.utils;

import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.entity.QuartzBean;
import org.quartz.*;

/**
 * Quartz的工具类
 * @author L.star
 * @date 2021/5/17 12:23
 */
@Slf4j
public class QuartzUtil {



    /**
     * 创建定时任务 创建后默认启动状态
     * @param scheduler 调度器
     * @param quartzBean 定时任务的信息类
     */
    public static void createJob(Scheduler scheduler, QuartzBean quartzBean) {
        try {
            //绝对路径名
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(quartzBean.getJobClass());
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(quartzBean.getJobName()).build();
            CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(quartzBean.getJobName()).withSchedule(cronSchedule).build();
            scheduler.scheduleJob(jobDetail,cronTrigger);
        } catch (ClassNotFoundException e) {
            log.error("错误：没有找到相关类,原因: {}",e.getLocalizedMessage());
            throw new BusinessException(ReturnCode.SCHEDULER_ERROR);
        } catch (SchedulerException e) {
            log.error("错误：定时任务创建失败,原因: {}",e.getMessage());
            throw new BusinessException(ReturnCode.SCHEDULER_ERROR);
        }
    }

    /**
     * 根据任务名暂停定时任务
     * @param scheduler 调度器
     * @param jobName 任务名
     */
    public static void pauseJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("错误：任务【{}】暂停失败,原因: {}",jobName,e.getMessage());
            throw new BusinessException(ReturnCode.SCHEDULER_ERROR);
        }
    }

    /**
     * 根据任务名回复任务
     * @param scheduler 调度器
     * @param jobName 任务名
     */
    public static void resumeJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error("错误：任务【{}】启动失败,原因: {}",jobName,e.getMessage());
            throw new BusinessException(ReturnCode.SCHEDULER_ERROR);
        }
    }

    /**
     * 根据任务名立即运行一次任务
     *
     * @param scheduler 调度器
     * @param jobName 任务名
     */
    public static void runOnce(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            log.error("错误：运行任务【{}】出错,原因: {}",jobName,e.getMessage());
            throw new BusinessException(ReturnCode.SCHEDULER_ERROR);
        }
    }

    /**
     * 跟新定时任务
     * @param scheduler 调度器
     * @param quartzBean 定时任务信息类
     */
    public static void updateJob(Scheduler scheduler, QuartzBean quartzBean) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(quartzBean.getJobName());
            CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(quartzBean.getCronExpression());
            CronTrigger cronTrigger =(CronTrigger)scheduler.getTrigger(triggerKey);
            cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronSchedule).build();
            scheduler.rescheduleJob(triggerKey,cronTrigger);
        } catch (SchedulerException e) {
            log.error("错误：更新定时任务【{}】失败,原因: {}",quartzBean,e.getMessage());
            throw new BusinessException(ReturnCode.SCHEDULER_ERROR);
        }
    }

    public static void delJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error("错误：删除任务【{}】失败 原因：{}",jobName,e.getMessage());
            throw new BusinessException(ReturnCode.SCHEDULER_ERROR);
        }
    }



}
