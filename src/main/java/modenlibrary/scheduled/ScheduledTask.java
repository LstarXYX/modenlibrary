package modenlibrary.scheduled;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.utils.RedisUtil;
import modenlibrary.entity.Lendhistory;
import modenlibrary.mapper.LendhistoryMapper;
import modenlibrary.service.LendhistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * @author L.star
 * @date 2020/12/25 20:42
 */
@Component
@Slf4j
public class ScheduledTask {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LendhistoryService lendhistoryService;


    /**
     * 每天凌晨12点保存前一天的redis的借阅人数
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void task1(){
        int num = 0;
        //获取昨天的人数
        Object obj = redisUtil.hget("LendBookNum"+DateUtil.thisMonth(),String.valueOf(DateUtil.yesterday().dayOfMonth()));
        if (obj != null){
            num = (int)obj;
        }
        Lendhistory yesterday = Lendhistory.builder()
                .cdate(DateUtil.yesterday())
                .people(num).build();
        //把昨天的借阅人数添加进去
        lendhistoryService.insert(yesterday);
        log.info("昨天的人数添加完成--------人数是："+num);
    }

    /**
     * 每月15号清楚上个月的LendBookNum
     */
    @Scheduled(cron = "0 0 0 15 * ?")
    public void task2(){
        redisUtil.del("LendBookNum"+DateUtil.lastMonth().monthBaseOne());
    }

}
