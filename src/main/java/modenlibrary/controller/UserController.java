package modenlibrary.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.Common.utils.PageUtils;
import modenlibrary.Common.utils.Result;
import modenlibrary.Common.vo.PageRequest;
import modenlibrary.Common.vo.PageResult;
import modenlibrary.Common.vo.ResultVo;
import modenlibrary.entity.Blacklist;
import modenlibrary.entity.User;
import modenlibrary.service.BlacklistService;
import modenlibrary.service.LendListService;
import modenlibrary.service.RoleService;
import modenlibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author L.star
 * @date 2020/12/23 18:41
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BlacklistService blacklistService;

    @Autowired
    private LendListService lendListService;


    /**
     * 根据条件查询用户（分页）
     * @param pageRequest
     * @param user
     * @return
     */
    @GetMapping("/queryAll")
    @ResponseBody
    public PageResult queryAll(PageRequest pageRequest, User user){
        return PageUtils.getPageResult(pageRequest,userService.queryUser(pageRequest,user));
    }

    /**
     * 添加用户
     *
     * @param username  姓名
     * @param password  密码
     * @param gender    性别 1男 0女
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public ResultVo add(String username,String password,Integer gender){
        User user = User.builder()
                .username(username)
                .password(password)
                .gender(gender.byteValue())
                .registerDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .build();
        Integer id = userService.insert(user);
        if (id != 1){
            throw new BusinessException(ReturnCode.SYSTEM_ERROR);
        }
        user = userService.selectByPrimaryKey(user.getId());
        if (user==null){
            throw new BusinessException(ReturnCode.SYSTEM_ERROR);
        }
        return Result.success(user);
    }

    /**
     * 根据id删除用户 假删除
     *
     * @param id
     * @return
     */
    @GetMapping("/del")
    @ResponseBody
    public ResultVo del(Integer id){
        User user = userService.selectByPrimaryKey(id);
        if (user == null) {
            throw new BusinessException(ReturnCode.NOT_USER);
        }
        user.setIsdel(Byte.valueOf("1"));
        int ok = userService.updateByPrimaryKey(user);
        if (ok!=1){
            throw new BusinessException(ReturnCode.SYSTEM_ERROR);
        }
        return Result.success(user);
    }

    /**
     * 更改用户信息
     *
     * @param user
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public ResultVo update(User user, HttpSession session){
        User u = (User) session.getAttribute("userInfo");
        //判断是否管理员
        if (userService.isAdmin(u.getId())){
            /**
             * 管理员更改其他用户其他信息
             *
             */
            if (!StrUtil.isBlankIfStr(user.getPassword())){
                //对密码加密
                user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            }
            int ok = userService.updateByPrimaryKeySelective(user);
            if (ok==1){
                //获取新user
                user = userService.selectByPrimaryKey(user.getId());
                return Result.success(user);
            }else {
                throw new BusinessException(ReturnCode.SYSTEM_ERROR);
            }
        }else {
            /**
             * 只能更改密码
             * todo:校验密码
             */
            if (!StringUtils.isEmpty(user.getPassword())){
                u.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
                userService.updateByPrimaryKeySelective(u);
                session.setAttribute("userInfo",u);
            }
            return Result.success(u);
        }
    }

    /**
     * 添加进黑名单
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/black/{id}")
    @ResponseBody
    public ResultVo addBlack(@PathVariable("id")Integer id){
        User user = userService.selectByPrimaryKey(id);
        if (user==null){
            throw new BusinessException(ReturnCode.NOT_USER);
        }
        //更新用户信息为黑名单
        user.setIsblack(Byte.valueOf("1"));
        int ok = userService.updateByPrimaryKeySelective(user);
        if (ok==1){
            ok = -1;
            Blacklist blacklist = Blacklist.builder()
                    .uid(user.getId())
                    .uname(user.getUsername())
                    .cdate(DateTime.now())
                    .build();
            //加入黑名单列表
            ok = blacklistService.insert(blacklist);
            if (ok == 1){
                return Result.success(blacklist);
            }else {
                throw new  BusinessException(ReturnCode.SYSTEM_ERROR);
            }
        }else {
            throw new  BusinessException(ReturnCode.SYSTEM_ERROR);
        }
    }

    /**
     * 移除黑名单
     *
     * @param id
     * @return
     */
    @GetMapping("/black/del")
    @ResponseBody
    public ResultVo delBlack(Integer id){
        User user = userService.selectByPrimaryKey(id);
        if (user==null){
            throw new BusinessException(ReturnCode.NOT_USER);
        }
        //更新用户信息
        user.setIsblack(Byte.valueOf("0"));
        userService.updateByPrimaryKeySelective(user);
        //从黑名单上删除
        int ok = blacklistService.del(user.getId());
        if (ok==1){
            return Result.success(user);
        }else {
            throw new BusinessException(ReturnCode.SYSTEM_ERROR);
        }
    }

    /**
     * 查询个人借书记录
     *
     * @param
     * @return
     */
    @GetMapping("/lendlist")
    @ResponseBody
    public PageResult lendlist(PageRequest pageRequest,HttpSession session){
        User user = (User) session.getAttribute("userInfo");
        return PageUtils.getPageResult(pageRequest,lendListService.queryById(pageRequest,user.getId()));
    }

}
