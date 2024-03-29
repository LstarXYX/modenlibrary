package modenlibrary.service.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.Common.utils.RedisUtil;
import modenlibrary.entity.Lendhistory;
import modenlibrary.entity.RangeResult;
import modenlibrary.service.BookService;
import modenlibrary.service.DataService;
import modenlibrary.service.LendhistoryService;
import modenlibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author L.star
 * @date 2020/12/26 10:08
 */
@Service("dataService")
@Slf4j
public class DataServiceImpl implements DataService {

    @Autowired
    private LendhistoryService lendhistoryService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 某天借阅人数的多少 默认当天
     * @return
     */
    @Override
    public Integer LendBookNumOfDay(String date) {
        return lendhistoryService.countDay(date);
    }

    /**
     * 某年某月份所有日期的借书人数
     *
     * @param YearMonth
     * @return
     */
    @Override
    public List<Lendhistory> LendBookNumOfAllDay(String YearMonth) {
        String[] temp = YearMonth.split("-");
        if (temp.length<2){
            throw new BusinessException(ReturnCode.FORM_ERROR);
        }
        String year = temp[0];
        String month = temp[1];
        log.info("查询 {} 年 {} 月 所有日期的借书人数",year,month);
        return lendhistoryService.countAllDay(year,month);
    }

    /**
     * 查询某一月份所有的借书人数
     *
     * @param Yearmonth
     * @return
     */
    @Override
    public Integer LendBookNumOfMonth(String Yearmonth) {
        String[] temp = Yearmonth.split("-");
        if (temp.length<2){
            throw new BusinessException(ReturnCode.FORM_ERROR);
        }
        String year = temp[0];
        String month = temp[1];
        log.info("查询 {} 年 {} 月 的借书人数",year,month);
        return lendhistoryService.countMonth(year,month);
    }

    @Override
    public List<RangeResult> LendBookNumOfRangeMonth(String from, String to) {
        log.info("查询 【{}】 到 【{}】的借书人数",from,to);
        return lendhistoryService.countRangeMonth(from,to);
    }

    @Override
    public Map<String, Integer> categoryNum() {
        //先查询redis有无
        Map<String,Integer> categoryNum = (Map<String, Integer>) redisUtil.hgetAll("categoryNum");
        Map<String,Integer>categories;
        if (categoryNum.size()==0){
            log.info("Redis没有类别数量的数据");
            //查找数据库
            categories = bookService.categoryNum();
            //放入redis
            for(Map.Entry<String, Integer>entry :categories.entrySet()){
                if (entry.getKey()==null){
                    continue;
                }
                redisUtil.hset("categoryNum",entry.getKey(),entry.getValue());
            }
        }else {
            log.info("使用缓存的类别数量的数据");
            //redis 有缓存
            categories = categoryNum;
        }
        return categories;
    }

    @Override
    public Map<String, Integer> categoryLendNum() {
        log.info("获取不同类别借书数量");
        return lendhistoryService.categoryLendNum();
    }

    @Override
    public Map<String, Integer> LendBookNumOfGender() {
        Map<String, Integer> obj = (Map<String, Integer>) redisUtil.hgetAll("lendbooknumgender");
        Map<String, Integer>map;
        if (obj.size()==0){
            log.info("Redis没有不同性别借书数量的数据");
            //数据库查找
            map = lendhistoryService.LendBookNumOfGender();
            for (Map.Entry<String, Integer>entry:map.entrySet()){
                if ("男".equals(entry.getKey())){
                    redisUtil.hset("lendbooknumgender","男",entry.getValue());
                }else {
                    redisUtil.hset("lendbooknumgender","女",entry.getValue());
                }
            }
        }else {
            log.info("使用Redis缓存不同性别借书数量的数据");
            map = obj;
        }
        return map;
    }

    @Override
    public Integer getUserNum() {
        log.info("获取用户数量");
        return userService.getUserNum();
    }
}
