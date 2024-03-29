package modenlibrary.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import modenlibrary.entity.SysLog;
import modenlibrary.mapper.SysLogMapper;
import modenlibrary.service.SysLogService;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/26 15:59
 */
@Slf4j
@Service
public class SysLogServiceImpl implements SysLogService{

    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return sysLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysLog record) {
        log.info("插入日志 【{}】",record);
        return sysLogMapper.insert(record);
    }

    @Override
    public int insertSelective(SysLog record) {
        return sysLogMapper.insertSelective(record);
    }

    @Override
    public SysLog selectByPrimaryKey(Long id) {
        return sysLogMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询某个ip的操作记录 只显示30条
     *
     * @param ip
     * @return
     */
    @Override
    public List<SysLog> queryByIp(String ip) {
        log.info("查询IP为 【{}】 的日志",ip);
        return sysLogMapper.querByIp(ip);
    }

    /**
     * 所有的操作记录 默认100条
     * @return
     */
    @Override
    public List<SysLog> queryAll(Integer num) {
        log.info("查询操作日志");
        if (num!=null && num>0){
            return sysLogMapper.queryAll(num);
        }else {
            return sysLogMapper.queryAll(100);
        }
    }
}
