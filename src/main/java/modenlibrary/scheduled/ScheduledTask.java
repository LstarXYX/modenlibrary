package modenlibrary.scheduled;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.utils.RedisUtil;
import modenlibrary.anno.Operation;
import modenlibrary.entity.Lendhistory;
import modenlibrary.mapper.LendhistoryMapper;
import modenlibrary.service.DataService;
import modenlibrary.service.LendhistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

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

    @Autowired
    private DataService dataService;

    /**
     * 每天凌晨12点保存前一天的redis的借阅人数
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void task1(){
        int num = 0;
        int pastday = DateUtil.yesterday().dayOfMonth();
        int pastmonth = DateUtil.thisMonth()+1;
        Object obj = null;
        if (DateUtil.thisDayOfMonth()==1){
            //如果今天是一号 那么昨天是上个月
            pastmonth -= 1;
            if (pastmonth == 0){
                pastmonth = 12;
            }
            //获取昨天的人数
            obj = redisUtil.hget("LendBookNum"+pastmonth, String.valueOf(pastday));
        }else {
            obj = redisUtil.hget("LendBookNum"+DateUtil.thisMonth()+1, String.valueOf(pastday));
        }
        if (obj != null){
            num = (int)obj;
        }
        Lendhistory yesterday = Lendhistory.builder()
                .cdate(DateUtil.yesterday())
                .people(num).build();
        //把昨天的借阅人数添加进去
        lendhistoryService.insert(yesterday);
//        把昨天的记录删除掉
        redisUtil.del("LendBookNum"+pastmonth,String.valueOf(pastday));
        log.info("昨天的人数添加完成--------人数是："+num);
    }

    /**
     * 每时自动更新redis字段
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void task3(){
//        不同类别书本借书数量
        //查找数据库
        Map<String,Integer> categories = dataService.categoryNum();
        //放入redis
        for(Map.Entry<String, Integer>entry :categories.entrySet()){
            if (entry.getKey()==null){
                continue;
            }
            redisUtil.hset("categoryNum",entry.getKey(),entry.getValue());
        }
//        男女生借书人数
        Map<String, Integer> genderlist = dataService.LendBookNumOfGender();
        //放入redis
        for(Map.Entry<String, Integer>entry :genderlist.entrySet()){
            redisUtil.hset("lendbooknumgender",entry.getKey(),entry.getValue());
        }
        //本月借书人数
        String yearMonth = DateUtil.thisYear()+"-"+DateUtil.thisMonth()+1;
        redisUtil.sSet(yearMonth, dataService.LendBookNumOfMonth(yearMonth));
    }

    /**
     * 每月15号清除上个月的LendBookNum
     */
    @Scheduled(cron = "0 0 0 15 * ?")
    public void task2(){
        redisUtil.del("LendBookNum"+DateUtil.lastMonth().monthBaseOne());
    }

}
