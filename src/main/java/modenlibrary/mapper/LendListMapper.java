package modenlibrary.mapper;

import modenlibrary.Common.eum.BookStatus;
import modenlibrary.entity.LendList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
public interface LendListMapper {
    int insert(LendList record);

    int insertSelective(LendList record);

    LendList queryLendBook(@Param("isbn")String isbn,@Param("uid")Integer uid);

    /**
     * 更新借阅信息
     * returndate
     * lenddate
     * status
     *
     * @param lendList
     * @return
     */
    int updateLendList(LendList lendList);

    List<LendList> queryAll(LendList lendList);

    List<LendList> queryById(Integer id);
}