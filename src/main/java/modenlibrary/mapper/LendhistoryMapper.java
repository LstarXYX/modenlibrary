package modenlibrary.mapper;

import modenlibrary.entity.Lendhistory;
import modenlibrary.entity.RangeResult;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * (Lendhistory)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-25 20:52:58
 */
public interface LendhistoryMapper {


    Integer countDay(String date);

    Integer insert(Lendhistory lendhistory);

    List<Lendhistory> countAllDay(@Param("year")String year,@Param("month")String month);

    Integer countMonth(@Param("year")String year,@Param("month")String month);

    List<RangeResult> countRangeMonth(@Param("from")String from, @Param("to")String to);

    List<Map<Object, Object>> LendBoookNumOfGender();

    List<Map<String, Object>> categoryLendNum();
}