package modenlibrary.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.Common.utils.RedisUtil;
import modenlibrary.Common.utils.Result;
import modenlibrary.Common.vo.ResultVo;
import modenlibrary.anno.Operation;
import modenlibrary.entity.Role;
import modenlibrary.entity.User;
import modenlibrary.entity.Vo.CodeVO;
import modenlibrary.entity.Vo.LoginVo;
import modenlibrary.service.RoleService;
import modenlibrary.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.aspectj.apache.bcel.classfile.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录
 * @author L.star
 * @date 2020/12/23 16:26
 */
@Controller
@CrossOrigin(originPatterns = "*",maxAge = 3600)
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    //验证码的宽高
    private static final Integer CODE_WIDTH=290;
    private static final Integer CODE_HEIGHT=60;
    //验证码过期时间(分钟)
    private static final Long CODE_EXPIRE_TIME=3L;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/403")
    public String not_auth(){
        return "/403.html";
    }

    @Autowired
    private RedisUtil redisUtil;

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
    @Operation("管理员登录")
    public ResultVo adminlogin(LoginVo loginVo){
        if (StrUtil.isBlankIfStr(loginVo.getUsername()) ||StrUtil.isBlankIfStr(loginVo.getPassword())){
            throw new BusinessException(ReturnCode.LOGIN_ERROR);
        }
        //验证验证码
        Object o = redisUtil.get(loginVo.getUuid());
        if (o==null){
            throw new BusinessException(ReturnCode.CODE_ERROR);
        }
        String code = String.valueOf(o);
        if (!code.equals(loginVo.getCode())) {
            throw new BusinessException(ReturnCode.CODE_ERROR);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(loginVo.getUsername(), loginVo.getPassword());
        Subject subject = SecurityUtils.getSubject();
        if(subject==null){
            throw new BusinessException(ReturnCode.SYSTEM_ERROR);
        }
        try {
            subject.login(token);
        }catch (Exception e){
            throw new BusinessException(ReturnCode.SYSTEM_ERROR);
        }
        User user = (User) subject.getSession().getAttribute("userInfo");
        Boolean isAdmin = userService.isAdmin(user.getId());
        if (!isAdmin){
            return Result.fail(ReturnCode.AUTHOR_ERROR);
        }
        Role role = roleService.selectByUserId(user.getId());
        logger.info("管理员登录");
        //传输map不太好....
        Map<String, Object>map = new HashMap<>();
        map.put("userInfo",user);
        map.put("role",role);
        return Result.success(map);
    }

    /**
     * 获取验证码
     * @return
     */
    @GetMapping("/code")
    @ResponseBody
    public ResultVo getCode(){
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(CODE_WIDTH, CODE_HEIGHT);
        String imageBase64 = lineCaptcha.getImageBase64Data();
        String uuid = IdUtil.simpleUUID();
        //构建返回类
        CodeVO codeVO = CodeVO.builder()
                .base64Str(imageBase64)
                .uuid(uuid).build();
        logger.info("uuid: {} 验证码：{}",uuid,lineCaptcha.getCode());
        //验证码放入redis并设置过期时间
        redisUtil.set(uuid,lineCaptcha.getCode(), TimeUnit.MINUTES.toSeconds(CODE_EXPIRE_TIME));
        return Result.success(codeVO);
    }

    @GetMapping("/logout")
    @ResponseBody
    public ResultVo logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.success(ReturnCode.SUCCESS);
    }

}
