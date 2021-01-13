package modenlibrary.service;

import modenlibrary.entity.Lendhistory;
import modenlibrary.entity.RangeResult;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * (Lendhistory)表服务接口
 *
 * @author makejava
 * @since 2020-12-25 20:53:05
 */
public interface LendhistoryService {

    Integer countDay(String date);

    Integer insert(Lendhistory lendhistory);

    List<Lendhistory> countAllDay(String year,String month);

    Integer countMonth(String year,String month);

    List<RangeResult> countRangeMonth(String from, String to);

    Map<String, Integer> LendBookNumOfGender();

    Map<String, Integer> categoryLendNum();
}