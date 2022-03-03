package modenlibrary.scheduled;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.utils.ApplicationContextUtil;
import modenlibrary.Common.utils.RedisUtil;
import modenlibrary.anno.Operation;
import modenlibrary.entity.Lendhistory;
import modenlibrary.mapper.LendhistoryMapper;
import modenlibrary.service.BookService;
import modenlibrary.service.DataService;
import modenlibrary.service.LendhistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务
 * @author L.star
 * @date 2020/12/25 20:42
 */
@Component
@Slf4j
public class ScheduledTask {


    private RedisUtil redisUtil = (RedisUtil) ApplicationContextUtil.getBean("redisUtil");


    private LendhistoryService lendhistoryService = (LendhistoryService) ApplicationContextUtil.getBean("lendhistoryService");


    private DataService dataService = (DataService) ApplicationContextUtil.getBean("dataService");

    private BookService bookService = (BookService) ApplicationContextUtil.getBean("bookService");

    private static int people = 0;

//    @Scheduled(cron = "0 0 0/1 * * ?")
    public void task0(){
        int m = DateUtil.thisMonth()+1;
        int d = DateUtil.thisDayOfMonth();
        Object obj = redisUtil.hget("LendBookNum" + m, String.valueOf(d));
        if (obj != null) {
            int num = (int) obj;
            people = num;
        }
        log.info("每小时播报 {}月{}日 借阅人数为：{} 人",m,d,obj==null?0:obj);
    }

    /**
     * 每天凌晨1点保存前一天的redis的借阅人数
     */
//    @Scheduled(cron = "0 50 23 * * ?")
    public void task1(){
        log.info("开始保存前一天借阅人数");
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
        log.info("今天日期: {}月{}日",pastmonth,pastday);
        //获取昨天的人数
        obj = redisUtil.hget("LendBookNum"+pastmonth, String.valueOf(pastday));
        log.info("获取到的obj值 {}",obj);
        if (obj != null){
            num = (int)obj;
            log.info("今天人数是：{}",num);
        }
        if (num == 0 && people != 0) {
            num = people;
            people = 0;
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

    /**
     * 每时自动更新redis字段
     */
//    @Scheduled(cron = "0 0 0/1 * * ?")
    public void task3(){
        log.info("每小时自动更新字段");
//        不同类别书本借书数量
        //查找数据库
        Map<String,Integer> categories;
        //查找数据库
        categories = bookService.categoryNum();
        log.info("查询数据库数据 {}",categories);
        //放入redis
        for(Map.Entry<String, Integer>entry :categories.entrySet()){
            if (entry.getKey()==null){
                continue;
            }
            redisUtil.hset("categoryNum",entry.getKey(),entry.getValue());
        }
//        男女生借书人数
        Map<String, Integer> genderlist;
        //数据库查找
        genderlist = lendhistoryService.LendBookNumOfGender();
        log.info("更新 男女生借书人数 【{}】 ",genderlist);
        for (Map.Entry<String, Integer>entry:genderlist.entrySet()){
            if ("男".equals(entry.getKey())){
                redisUtil.hset("lendbooknumgender","男",entry.getValue());
            }else {
                redisUtil.hset("lendbooknumgender","女",entry.getValue());
            }
        }
        //放入redis
        /*for(Map.Entry<String, Integer>entry :genderlist.entrySet()){
            redisUtil.hset("lendbooknumgender",entry.getKey(),entry.getValue());
        }*/
        //本月借书人数
        String yearMonth = DateUtil.thisYear()+"-"+(DateUtil.thisMonth()+1);
        log.info("更新 本月借书人数 【{}】",yearMonth);
        redisUtil.sSet(yearMonth, dataService.LendBookNumOfMonth(yearMonth));
    }

    /**
     * 每月15号清除上个月的LendBookNum
     */
//    @Scheduled(cron = "0 0 0 15 * ?")
    public void task2(){
        log.info("清除日期 {} 的借书人数",DateUtil.lastMonth().monthBaseOne());
        redisUtil.del("LendBookNum"+DateUtil.lastMonth().monthBaseOne());
    }
}
