package modenlibrary.scheduled.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.utils.RedisUtil;
import modenlibrary.anno.Operation;
import modenlibrary.service.BookService;
import modenlibrary.service.DataService;
import modenlibrary.service.LendhistoryService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 更新字段的任务
 * @author L.star
 * @date 2021/5/17 13:29
 */
@Operation("更新字段")
@Component
@Slf4j
public class updateTask extends QuartzJobBean {

    @Autowired
    private BookService bookService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LendhistoryService lendhistoryService;
    @Autowired
    private DataService dataService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("自动更新字段");
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
}
