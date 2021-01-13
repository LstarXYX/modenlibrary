package modenlibrary.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import modenlibrary.Common.vo.PageRequest;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import modenlibrary.entity.Blacklist;
import modenlibrary.mapper.BlacklistMapper;
import modenlibrary.service.BlacklistService;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
@Service
public class BlacklistServiceImpl implements BlacklistService{

    @Resource
    private BlacklistMapper blacklistMapper;

    @Override
    public int insert(Blacklist record) {
        return blacklistMapper.insert(record);
    }

    @Override
    public int insertSelective(Blacklist record) {
        return blacklistMapper.insertSelective(record);
    }

    @Override
    public int del(Integer id) {
        return blacklistMapper.del(id);
    }

    @Override
    public PageInfo<Blacklist> list(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<Blacklist>list = blacklistMapper.list();
        return new PageInfo<>(list);
    }
}
