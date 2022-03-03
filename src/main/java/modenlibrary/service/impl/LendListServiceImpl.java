package modenlibrary.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.vo.PageRequest;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import modenlibrary.mapper.LendListMapper;
import modenlibrary.entity.LendList;
import modenlibrary.service.LendListService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
@Slf4j
@Service
public class LendListServiceImpl implements LendListService{

    @Resource
    private LendListMapper lendListMapper;

    @Transactional
    @Override
    public int insert(LendList record) {
        return lendListMapper.insert(record);
    }

    @Transactional
    @Override
    public int insertSelective(LendList record) {
        return lendListMapper.insertSelective(record);
    }

    /**
     * 查询借阅列表 分页
     * @param pageRequest
     * @param lendList
     * @return
     */
    @Override
    public PageInfo<LendList> queryAll(PageRequest pageRequest, LendList lendList) {
        log.info("查询借阅列表 【{}】",lendList);
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<LendList> list = lendListMapper.queryAll(lendList);
        return new PageInfo<>(list);
    }

    /**
     * 根据id查询借书记录
     *
     * @param id
     * @return
     */
    @Override
    public PageInfo<LendList> queryById(PageRequest pageRequest,Integer id) {
        log.info("根据ID查询借阅记录,ID为 【{}】",id);
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<LendList>list = lendListMapper.queryById(id);
        return new PageInfo<>(list);
    }
}
