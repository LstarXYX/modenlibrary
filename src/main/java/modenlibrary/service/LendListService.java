package modenlibrary.service;

import com.github.pagehelper.PageInfo;
import modenlibrary.Common.vo.PageRequest;
import modenlibrary.entity.LendList;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
public interface LendListService{


    int insert(LendList record);

    int insertSelective(LendList record);

    PageInfo<LendList> queryAll(PageRequest pageRequest, LendList lendList);

    PageInfo<LendList> queryById(PageRequest pageRequest,Integer id);
    }
