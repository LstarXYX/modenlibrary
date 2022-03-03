package modenlibrary.controller;

import cn.hutool.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.utils.Result;
import modenlibrary.Common.vo.ResultVo;
import modenlibrary.anno.Operation;
import modenlibrary.entity.QuartzBean;
import modenlibrary.service.SchedulerService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author L.star
 * @date 2021/5/17 12:49
 */
@Slf4j
@RestController
@RequestMapping("/job")
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Operation("创建定时任务")
    @RequiresRoles("超级管理员")
    public ResultVo create(QuartzBean quartzBean) {
        schedulerService.createJob(quartzBean);
        return Result.success(ReturnCode.SUCCESS);
    }

    @RequestMapping(value = "/pause", method = RequestMethod.POST)
    @Operation("暂停定时任务")
    @RequiresRoles("超级管理员")
    public ResultVo pause(QuartzBean quartzBean) {
        schedulerService.pauseJob(quartzBean);
        return Result.success(ReturnCode.SUCCESS);
    }

    @RequestMapping(value = "/resume", method = RequestMethod.POST)
    @Operation("恢复启动定时任务")
    @RequiresRoles("超级管理员")
    public ResultVo resume(QuartzBean quartzBean) {
        schedulerService.resumeJob(quartzBean);
        return Result.success(ReturnCode.SUCCESS);
    }

    @RequestMapping(value = "/run", method = RequestMethod.POST)
    @Operation("运行一次定时任务")
    @RequiresRoles("超级管理员")
    public ResultVo runOnce(QuartzBean quartzBean) {
        schedulerService.runOnce(quartzBean);
        return Result.success(ReturnCode.SUCCESS);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Operation("更新定时任务")
    @RequiresRoles("超级管理员")
    public ResultVo update(QuartzBean quartzBean) {
        schedulerService.updateJob(quartzBean);
        return Result.success(ReturnCode.SUCCESS);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @Operation("删除定时任务")
    @RequiresRoles("超级管理员")
    public ResultVo del(QuartzBean quartzBean) {
        schedulerService.delJob(quartzBean);
        return Result.success(ReturnCode.SUCCESS);
    }

    /**
     * 获取定时任务列表
     * @return
     */
    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    @RequiresRoles("超级管理员")
    public ResultVo jobs() {
        List<QuartzBean> list = schedulerService.getJobs();
        return Result.success(list);
    }

    @RequestMapping(value = "/class",method = RequestMethod.GET)
    @RequiresRoles("超级管理员")
    public ResultVo classPath(){
        log.info("查询类路径");
        Map<String, String> map = new HashMap<>();
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper("modenlibrary.scheduled.task", QuartzJobBean.class);
        for (Class<?> aClass : classes) {
            String desc = aClass.getAnnotation(Operation.class).value();
            String absPath = ClassUtil.getPackagePath(aClass);
            String name = aClass.getName();
            map.put(name,desc);
        }
        log.info("查询的类路径：{}",map);
        return Result.success(map);
    }

}
