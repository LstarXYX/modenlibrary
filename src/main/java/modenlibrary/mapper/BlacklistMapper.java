package modenlibrary.mapper;

import modenlibrary.entity.Blacklist;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
public interface BlacklistMapper {
    int insert(Blacklist record);

    int insertSelective(Blacklist record);

    int del(Integer id);

    List<Blacklist> list();
}