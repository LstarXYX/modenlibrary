package modenlibrary.controller;

import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.Common.utils.Result;
import modenlibrary.Common.vo.ResultVo;
import modenlibrary.anno.Operation;
import modenlibrary.entity.Role;
import modenlibrary.entity.User;
import modenlibrary.service.RoleService;
import modenlibrary.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 登录
 * @author L.star
 * @date 2020/12/23 16:26
 */
@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/403")
    public String not_auth(){
        return "/403.html";
    }

    /**
     * 未登录返回
     * @return
     */
    @RequestMapping("/un_auth")
    @ResponseBody
    public ResultVo un_auth(){
        return Result.fail(ReturnCode.UNAUTH);
    }

    /**
     * 没有权限返回
     * @return
     */
    @RequestMapping("/unauthorized")
    @ResponseBody
    public ResultVo unauthorized(){
        return Result.fail(ReturnCode.AUTHOR_ERROR);
    }

    /**
     * 前台登录
     * @return
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public ResultVo Indexlogin(String username, String password){
        if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            throw new BusinessException(ReturnCode.LOGIN_ERROR);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        if (subject==null){
            throw new BusinessException(ReturnCode.SYSTEM_ERROR);
        }
//        User user = userService.login(id, password);
//        if (user != null) {
//            session.setAttribute("userInfo",user);
//        }else {
//            throw new BusinessException(ReturnCode.LOGIN_ERROR);
//        }
        try {
            subject.login(token);
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(ReturnCode.LOGIN_ERROR);
        }
        logger.info("登录成功");
        User user = (User) subject.getSession().getAttribute("userInfo");
        return Result.success(user);
    }

    @PostMapping(value = "/adminlogin")
    @ResponseBody
    public ResultVo adminlogin(Integer id, String password, HttpSession session){
        if (StringUtils.isEmpty(id)||StringUtils.isEmpty(password)){
            throw new BusinessException(ReturnCode.LOGIN_ERROR);
        }
        User user = userService.loginAdmin(id, password);
        if (user != null) {
            //不为空 代表管理员
            session.setAttribute("userInfo",user);
        }else {
            throw new BusinessException(ReturnCode.LOGIN_ERROR);
        }
        logger.info("管理员登录");
        return Result.success(user);
    }


}
