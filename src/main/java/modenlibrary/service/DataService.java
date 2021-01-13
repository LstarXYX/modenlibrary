package modenlibrary.service;

import modenlibrary.Common.vo.ResultVo;
import modenlibrary.entity.Lendhistory;
import modenlibrary.entity.RangeResult;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 统计数据的接口
 * @author L.star
 * @date 2020/12/26 10:06
 */
public interface DataService {

    Integer LendBookNumOfDay(String date);

    List<Lendhistory> LendBookNumOfAllDay(String YearMonth);

    Integer LendBookNumOfMonth(String Yearmonth);

    List<RangeResult> LendBookNumOfRangeMonth(String from, String to);

    Map<String, Integer> categoryNum();

    Map<String, Integer> LendBookNumOfGender();

    Integer getUserNum();

    Map<String, Integer> categoryLendNum();
}
