package modenlibrary.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.eum.RoleEnum;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.Common.vo.PageRequest;
import modenlibrary.entity.Role;
import modenlibrary.entity.Vo.UserInfo;
import modenlibrary.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import modenlibrary.mapper.UserMapper;
import modenlibrary.entity.User;
import modenlibrary.service.UserService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 添加用户 把角色也添加进角色用户表
     * @param record
     * @return
     */
    @Transactional
    @Override
    public int insert(User record) {
        int ok = userMapper.insert(record);
        if (ok==1){
           ok = userMapper.addReaderRole(record.getId());
        }
        return ok;
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public User login(Integer id, String password) {
        return userMapper.login(id,password);
    }

    @Override
    public User loginAdmin(Integer id, String password) {
        User user = userMapper.login(id, password);
        if (user!=null){
            //有该用户 判断是否是管理员
            Role role = roleMapper.isAdmin(user.getId());
            //如果是读者 返回空
            if (role.getRoleName().equals(RoleEnum.READER.getName())){
                return null;
            }else {
                //其他表示是管理员或以上
                return user;
            }
        }
        return null;
    }

    @Override
    public PageInfo<UserInfo> queryUser(PageRequest pageRequest,User user) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum,pageSize);
//        List<User>users = userMapper.queryUser(user);
        List<UserInfo>users = userMapper.queryUser(user);
        return new PageInfo<>(users);
    }

    @Override
    public Boolean isAdmin(Integer id) {
        Role role = roleMapper.isAdmin(id);
        if (role==null){
            throw new BusinessException(ReturnCode.NOT_USER);
        }
        return (role.getRoleName().equals(RoleEnum.ADMIN.getName())||role.getRoleName().equals(RoleEnum.SPADMIN.getName()));
    }

    @Override
    public User selectByUserName(String username) {
        return userMapper.selectByUserName(username);
    }

    /**
     * 把用户角色变为黑名单角色
     * @param id
     * @return
     */
    @Override
    public int changeRole(Integer id,Integer roleId) {
        return userMapper.changeRole(id,roleId);
    }

    @Override
    public Integer getUserNum() {
        return userMapper.getUserNum();
    }
}
