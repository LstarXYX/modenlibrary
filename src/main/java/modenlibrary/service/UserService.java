package modenlibrary.service;

import com.github.pagehelper.PageInfo;
import modenlibrary.Common.vo.PageRequest;
import modenlibrary.entity.User;
import modenlibrary.entity.Vo.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
public interface UserService{


    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User login(Integer id, String password);

        /**
         * 是否是管理员
         * @param id
         * @param password
         * @return
         */
    User loginAdmin(Integer id, String password);

    /**
     * 根据条件查询用户
     *
     * @param user
     * @return
     */
    PageInfo<UserInfo> queryUser(PageRequest pageRequest, User user);

    Boolean isAdmin(Integer id);

    User selectByUserName(String username);

    int changeRole(Integer id,Integer roleId);

    Integer getUserNum();

    void insertUsers(MultipartFile file);

}
