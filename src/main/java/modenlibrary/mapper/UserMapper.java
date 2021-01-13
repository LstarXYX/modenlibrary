package modenlibrary.mapper;

import modenlibrary.Common.vo.PageRequest;
import modenlibrary.entity.User;
import modenlibrary.entity.Vo.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加用户 只需要 username，password，gender，registerDate
     * @param record
     * @return
     */
    int insert(User record);

    int addReaderRole(Integer userId);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User login(@Param("id")Integer id,@Param("password")String password);

    /**
     * 根据条件查询user
     * @param user
     * @return
     */
    List<UserInfo> queryUser(User user);

    User selectByUserName(String username);

    int changeRole(@Param("id")Integer id,@Param("roleId")Integer roleId);

    Integer getUserNum();
}