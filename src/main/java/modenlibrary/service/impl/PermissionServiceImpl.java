package modenlibrary.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import modenlibrary.entity.Permission;
import modenlibrary.mapper.PermissionMapper;
import modenlibrary.service.PermissionService;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
@Service
public class PermissionServiceImpl implements PermissionService{

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public int deleteByPrimaryKey(Integer permissionId) {
        return permissionMapper.deleteByPrimaryKey(permissionId);
    }

    @Override
    public int insert(Permission record) {
        return permissionMapper.insert(record);
    }

    @Override
    public int insertSelective(Permission record) {
        return permissionMapper.insertSelective(record);
    }

    @Override
    public Permission selectByPrimaryKey(Integer permissionId) {
        return permissionMapper.selectByPrimaryKey(permissionId);
    }

    @Override
    public int updateByPrimaryKeySelective(Permission record) {
        return permissionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Permission record) {
        return permissionMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Permission> getPermissionsByUserId(Integer id) {
        return permissionMapper.getPermissionsByUserId(id);
    }
}
