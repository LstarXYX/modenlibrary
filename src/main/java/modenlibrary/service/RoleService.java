package modenlibrary.service;

import modenlibrary.entity.Role;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
public interface RoleService{


    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> getRolesByUserId(Integer id);

    Role selectByUserId(Integer id);
}
