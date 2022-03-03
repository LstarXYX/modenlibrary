package modenlibrary.service.impl;

import lombok.extern.slf4j.Slf4j;
import modenlibrary.entity.Lendhistory;
import modenlibrary.entity.RangeResult;
import modenlibrary.mapper.LendhistoryMapper;
import modenlibrary.service.LendhistoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author L.star
 * @date 2020/12/25 20:57
 */
@Service("lendhistoryService")
@Slf4j
public class LendhistoryServiceImpl implements LendhistoryService {

    @Resource
    private LendhistoryMapper lendhistoryMapper;

    /**
     * 查询某一天借阅的人数
     *
     * @param date String
     * @return
     */
    @Override
    public Integer countDay(String date) {
        log.info("查询 【{}】 的借阅人数",date);
        return lendhistoryMapper.countDay(date);
    }

    @Override
    public List<Lendhistory> countAllDay(String year,String month) {
        log.info("查询 {} 年 {} 月 的借书人数",year,month);
        return lendhistoryMapper.countAllDay(year,month);
    }

    @Override
    public Integer countMonth(String year,String month) {
        log.info("统计 {} 年 {} 月的借书人数",year,month);
        return lendhistoryMapper.countMonth(year,month);
    }

    /**
     * 添加当天的借阅人数
     *
     * @param lendhistory
     * @return
     */
    @Override
    public Integer insert(Lendhistory lendhistory) {
        log.info("添加当天借书人数 【{}】",lendhistory);
        return lendhistoryMapper.insert(lendhistory);
    }

    /**
     * 查询某年某月到某年某月的借阅人数列表 按月份总数
     * @param from  yyyy-mm
     * @param to    yyyy-mm
     * @return
     */
    @Override
    public List<RangeResult> countRangeMonth(String from, String to) {
        log.info("查询 【{}】 到 【{}】 的借书人数列表",from,to);
        return lendhistoryMapper.countRangeMonth(from,to);
    }

    /**
     * 查询男女生借书人数
     *
     * @return
     */
    @Override
    public Map<String, Integer> LendBookNumOfGender() {
        log.info("查询男女生借书人数");
        Map<String, Integer>map = new HashMap<>();
        List<Map<Object, Object>>list = lendhistoryMapper.LendBoookNumOfGender();
        for (Map<Object, Object> stringObjectMap : list) {
            String gender = null;
            Integer num = 0;
            for(Map.Entry<Object,Object>entry : stringObjectMap.entrySet()){
                if ("gender".equals(String.valueOf(entry.getKey()))){
                    gender = Integer.parseInt(entry.getValue()+"")==1?"男":"女";
                }else if ("num".equals(entry.getKey())){
                    num = Integer.parseInt((entry.getValue() + ""));
                }
                map.put(gender,num);
            }
        }
        return map;
    }

    /**
     * 不同类型书本的借书人数
     * @return
     */
    @Override
    public Map<String, Integer> categoryLendNum() {
        log.info("查询不同类型图书借书人数");
        Map<String, Integer>map = new HashMap<>();
        List<Map<String, Object>>list = lendhistoryMapper.categoryLendNum();
        for (Map<String, Object> stringObjectMap : list) {
            String cname = null;
            Integer num = 0;
            for(Map.Entry<String,Object>entry : stringObjectMap.entrySet()){
                if ("c_name".equals(entry.getKey())){
                    cname = (String) entry.getValue();
                }else if ("num".equals(entry.getKey())){
                    num = Integer.parseInt((entry.getValue() + ""));
                }
                map.put(cname,num);
            }
        }
        map.remove(null);
        return map;
    }
}
