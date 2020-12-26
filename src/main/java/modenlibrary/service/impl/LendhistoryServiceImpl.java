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
import java.util.List;

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
}
