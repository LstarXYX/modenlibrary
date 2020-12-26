package modenlibrary.mapper;

import modenlibrary.entity.SysLog;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/26 15:59
 */
public interface SysLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

    List<SysLog> querByIp(String ip);

    List<SysLog> queryAll();
}