package modenlibrary.service;

import modenlibrary.entity.Permission;
    /**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
public interface PermissionService{


    int deleteByPrimaryKey(Integer permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer permissionId);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

}
