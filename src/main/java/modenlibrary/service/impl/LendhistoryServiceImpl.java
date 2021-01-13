package modenlibrary.service.impl;

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
@Service
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
        return lendhistoryMapper.countDay(date);
    }

    @Override
    public List<Lendhistory> countAllDay(String year,String month) {
        return lendhistoryMapper.countAllDay(year,month);
    }

    @Override
    public Integer countMonth(String year,String month) {
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
        return lendhistoryMapper.countRangeMonth(from,to);
    }

    /**
     * 查询男女生借书人数
     *
     * @return
     */
    @Override
    public Map<String, Integer> LendBookNumOfGender() {
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
