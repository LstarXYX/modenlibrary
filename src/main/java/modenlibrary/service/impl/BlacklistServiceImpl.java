package modenlibrary.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.vo.PageRequest;
import org.springframework.scheduling.annotation.Async;
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
@Slf4j
@Service
public class BlacklistServiceImpl implements BlacklistService{

    @Resource
    private BlacklistMapper blacklistMapper;

    @Async("asyncServiceExecutor")
    @Override
    public void insert(Blacklist record) {
        log.info("添加黑名单 {}",record);
        blacklistMapper.insert(record);
    }

    @Override
    public int insertSelective(Blacklist record) {
        log.info("有选择地添加黑名单 {}",record);
        return blacklistMapper.insertSelective(record);
    }

    @Async("asyncServiceExecutor")
    @Override
    public void del(Integer id) {
        log.info("删除ID为 【{}】 的黑名单",id);
        blacklistMapper.del(id);
    }

    @Override
    public PageInfo<Blacklist> list(PageRequest pageRequest) {
        log.info("查询黑名单列表");
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<Blacklist>list = blacklistMapper.list();
        return new PageInfo<>(list);
    }
}
