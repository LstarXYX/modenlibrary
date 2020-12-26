package modenlibrary.service.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.entity.Lendhistory;
import modenlibrary.entity.RangeResult;
import modenlibrary.service.DataService;
import modenlibrary.service.LendhistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author L.star
 * @date 2020/12/26 10:08
 */
@Service
@Slf4j
public class DataServiceImpl implements DataService {

    @Autowired
    private LendhistoryService lendhistoryService;

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
        log.info("年："+year+"----月："+month);
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
        log.info("年："+year+"----月："+month);
        return lendhistoryService.countMonth(year,month);
    }

    @Override
    public List<RangeResult> LendBookNumOfRangeMonth(String from, String to) {
        return lendhistoryService.countRangeMonth(from,to);
    }
}
