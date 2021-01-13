package modenlibrary.service;

import modenlibrary.entity.SysLog;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/26 15:59
 */
public interface SysLogService{


    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    List<SysLog> queryByIp(String ip);

    List<SysLog> queryAll(Integer num);
}
