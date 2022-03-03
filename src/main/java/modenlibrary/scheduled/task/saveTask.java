package modenlibrary.scheduled.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.utils.RedisUtil;
import modenlibrary.anno.Operation;
import modenlibrary.entity.Lendhistory;
import modenlibrary.service.LendhistoryService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 每天保存redis的借阅人数的task
 * @author L.star
 * @date 2021/5/17 12:16
 */
@Component
@Slf4j
@Operation("保存借阅人数")
public class saveTask extends QuartzJobBean {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LendhistoryService lendhistoryService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始保存当天借阅人数");
        int num = 0;
//        int pastday = DateUtil.yesterday().dayOfMonth();
        int pastday = DateUtil.thisDayOfMonth();
        int pastmonth = DateUtil.thisMonth()+1;
        Object obj = null;
//        if (DateUtil.thisDayOfMonth()==1){
//            log.info("获取上个月日期");
//            //如果今天是一号 那么昨天是上个月
//            pastmonth -= 1;
//            if (pastmonth == 0){
//                pastmonth = 12;
//            }
//        }
        log.info("保存日期: {}月{}日",pastmonth,pastday);
        //获取昨天的人数
        obj = redisUtil.hget("LendBookNum"+pastmonth, String.valueOf(pastday));
        log.info("获取到的obj值 {}",obj);
        if (obj != null){
            num = (int)obj;
            log.info("今天人数是：{}",num);
        }
        Lendhistory yesterday = Lendhistory.builder()
                .cdate(DateUtil.date())
                .people(num).build();
        log.info("插入记录 {}",yesterday);
        //把今天的借阅人数添加进去
        lendhistoryService.insert(yesterday);
        log.info("删除 {}月{}日的缓存记录",pastmonth,pastday);
//        把昨天的记录删除掉
        redisUtil.del("LendBookNum"+pastmonth,String.valueOf(pastday));
        log.info("今天的人数添加完成--------人数是："+num);
    }
}
