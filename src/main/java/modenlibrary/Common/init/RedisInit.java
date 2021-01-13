package modenlibrary.Common.init;

import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.utils.RedisUtil;
import modenlibrary.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 程序启动后自动执行Redis初始化方法
 * @author L.star
 * @date 2021/1/5 14:17
 */
@Component
@Slf4j
@Order(10)
public class RedisInit implements ApplicationRunner {

    @Autowired
    private DataService dataService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Redis初始化");
        //查找数据库
        Map<String,Integer> categories = dataService.categoryNum();
        //放入redis
        for(Map.Entry<String, Integer>entry :categories.entrySet()){
            if (entry.getKey()==null){
                continue;
            }
            redisUtil.hset("categoryNum",entry.getKey(),entry.getValue());
        }
        Map<String, Integer> genderlist = dataService.LendBookNumOfGender();
        //放入redis
        for(Map.Entry<String, Integer>entry :genderlist.entrySet()){
            redisUtil.hset("lendbooknumgender",entry.getKey(),entry.getValue());
        }
        log.info("Redis初始化完成");
    }
}
