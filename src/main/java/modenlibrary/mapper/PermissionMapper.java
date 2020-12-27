package modenlibrary.mapper;

import modenlibrary.entity.Permission;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer permissionId);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> getPermissionsByUserId(Integer id);
}