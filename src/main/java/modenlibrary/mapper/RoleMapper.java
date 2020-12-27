package modenlibrary.mapper;

import modenlibrary.entity.Role;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 判断是否管理员
     * @param id
     * @return
     */
    Role isAdmin(Integer id);

    List<Role> getRolesByUserId(Integer id);

    Role selectByUserId(Integer id);
}