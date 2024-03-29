package modenlibrary;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.utils.ApplicationContextUtil;
import modenlibrary.Common.utils.RedisUtil;
import modenlibrary.entity.Lendhistory;
import modenlibrary.scheduled.ScheduledTask;
import modenlibrary.service.DataService;
import modenlibrary.service.LendhistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@Slf4j
class ModenlibraryApplicationTests {

//    @Autowired
//    private RedisUtil redisUtil;
////
//    @Autowired
//    private LendhistoryService lendhistoryService;
//
//    @Test
//    void contextLoads() {
//        System.out.println("thisMonth---"+DateUtil.thisMonth());
//        System.out.println("lastMonth---"+DateUtil.lastMonth());
//        System.out.println("thisDayOfMonth---"+DateUtil.thisDayOfMonth());
//        System.out.println("lastMonth().monthBaseOne()---"+DateUtil.lastMonth().monthBaseOne());
//        System.out.println("DateUtil.yesterday().dayOfMonth()---"+DateUtil.yesterday().dayOfMonth());
//    }
//
//    @Test
//    void testRedis(){
//        redisUtil.hincr("LendBookNum"+DateUtil.thisMonth(),String.valueOf(DateUtil.thisDayOfMonth()-1),1);
//        redisUtil.hincr("LendBookNum"+DateUtil.lastMonth().monthBaseOne(),String.valueOf(DateUtil.thisDayOfMonth()),1);
//        //获取昨天的人数
//        int num = (int)redisUtil.hget("LendBookNum"+ DateUtil.thisMonth(),String.valueOf(DateUtil.thisDayOfMonth()));
//        Lendhistory today = Lendhistory.builder()
//                .cdate(DateUtil.yesterday())
//                .people(num).build();
//        //把昨天的借阅人数添加进去
//        lendhistoryService.insert(today);
//        log.info("今天的人数添加完成--------人数是："+num);
//        //删除上个月的
//        redisUtil.del("LendBookNum"+DateUtil.lastMonth());
//        log.info(""+ (int)redisUtil.hget("LendBookNum"+ DateUtil.lastMonth().monthBaseOne(),String.valueOf(DateUtil.thisDayOfMonth())));
//    }

//    @Test
//    void testScheduledTask(){
//        int num = 0;
//        int pastday = DateUtil.yesterday().dayOfMonth();
//        int pastmonth = DateUtil.thisMonth()+1;
//        if (DateUtil.thisDayOfMonth()==1){
//            //如果今天是一号 那么昨天是上个月
//            pastmonth -= 1;
//            if (pastmonth == 0){
//                pastmonth = 12;
//            }
//        }
//        //获取昨天的人数
//        Object obj = redisUtil.hget("LendBookNum"+pastmonth, String.valueOf(pastday));
//        if (obj != null){
//            num = (int)obj;
//        }
//        Lendhistory yesterday = Lendhistory.builder()
//                .cdate(DateUtil.yesterday())
//                .people(num).build();
//        //把昨天的借阅人数添加进去
//        lendhistoryService.insert(yesterday);
////        把昨天的记录删除掉
//        redisUtil.del("LendBookNum"+pastmonth,String.valueOf(pastday));
//        log.info("昨天的人数添加完成--------人数是："+num);
//    }


//    @Test
//    public void task1Test(){
//        int num = 0;
//        int pastday = DateUtil.yesterday().dayOfMonth();
//        log.info("yesterday---"+pastday);
//        int pastmonth = DateUtil.thisMonth()+1;
//        log.info("thisMonth---"+pastmonth);
//        if (DateUtil.thisDayOfMonth()==1){
//            //如果今天是一号 那么昨天是上个月
//            pastmonth -= 1;
//            if (pastmonth == 0){
//                pastmonth = 12;
//            }
//        }
//        log.info("thisMonth---"+pastmonth);
//        //获取昨天的人数
//        Object obj = redisUtil.hget("LendBookNum"+pastmonth, String.valueOf(pastday));
//        if (obj != null){
//            num = (int)obj;
//        }
//        log.info("num---"+num);
//        Lendhistory yesterday = Lendhistory.builder()
//                .cdate(DateUtil.yesterday())
//                .people(num).build();
//        //把昨天的借阅人数添加进去
//        lendhistoryService.insert(yesterday);
////        把昨天的记录删除掉
//        redisUtil.del("LendBookNum"+pastmonth,String.valueOf(pastday));
//        log.info("昨天的人数添加完成--------人数是："+num);
//    }

//
//    @Autowired
//    private DataService dataService;
//
//    @Test
//    public void testBoysAndGirlsNum(){
//        Map<String, Integer> map = dataService.LendBookNumOfGender();
//        for(Map.Entry entry : map.entrySet()){
//            System.out.println("key--"+entry.getKey()+",value---"+entry.getValue());
//        }
//    }
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @Test
//    public void testRedis(){
//        if (redisUtil == null) {
//            assert false;
//        }
//        int num = 0;
//        int pastday = DateUtil.yesterday().dayOfMonth();
//        int pastmonth = DateUtil.thisMonth()+1;
//        Object obj = null;
//        if (DateUtil.thisDayOfMonth()==1){
//            log.info("获取上个月日期");
//            //如果今天是一号 那么昨天是上个月
//            pastmonth -= 1;
//            if (pastmonth == 0){
//                pastmonth = 12;
//            }
//        }
//        log.info("昨天日期: {}月{}日",pastmonth,pastday);
//        //获取昨天的人数
//        obj = redisUtil.hget("LendBookNum"+pastmonth, String.valueOf(pastday));
//        log.info("获取到的obj值 {}",obj);
//        if (obj != null){
//            num = (int)obj;
//            log.info("昨天人数是：{}",num);
//        }
//        Lendhistory yesterday = Lendhistory.builder()
//                .cdate(DateUtil.yesterday())
//                .people(num).build();
//
//    }

/*    @Autowired
    private ScheduledTask scheduledTask;

    @Test
    public void testScheduled(){
        scheduledTask.task1();
    }*/

}
