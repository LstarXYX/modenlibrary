package modenlibrary.service;

import com.github.pagehelper.PageInfo;
import modenlibrary.Common.vo.PageRequest;
import modenlibrary.entity.Blacklist;
    /**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
public interface BlacklistService{


    int insert(Blacklist record);

    int insertSelective(Blacklist record);

    int del(Integer id);

    PageInfo<Blacklist> list(PageRequest pageRequest);
    }
