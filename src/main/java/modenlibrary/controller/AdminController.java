package modenlibrary.controller;

import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.eum.RoleEnum;
import modenlibrary.Common.socket.WebSocketServer;
import modenlibrary.Common.utils.Result;
import modenlibrary.Common.vo.ResultVo;
import modenlibrary.anno.Operation;
import modenlibrary.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 超级管理员api
 * @author L.star
 * @date 2020/12/27 21:20
 */
@Controller
@RequestMapping("/admin")
@CrossOrigin(originPatterns = "*",maxAge = 3600)
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * 用于超级管理员添加管理员
     * @param id
     * @return
     */
    @GetMapping("/add/{id}")
    @Operation("/添加管理员")
    @ResponseBody
    @RequiresRoles("超级管理员")
    public ResultVo addAdmin(@PathVariable("id")Integer id){
        userService.changeRole(id, RoleEnum.ADMIN.getRoleid());
        return Result.success("ok");
    }

    /**
     * 移除管理员
     *
     * @param id
     * @return
     */
    @GetMapping("/remove/{id}")
    @Operation("/移除管理员")
    @ResponseBody
    @RequiresRoles("超级管理员")
    public ResultVo removeAdmin(@PathVariable("id")Integer id){
        userService.changeRole(id,RoleEnum.READER.getRoleid());
        return Result.success("ok");
    }

    @GetMapping("/push")
    @Operation("/推送信息")
    @ResponseBody
    @RequiresRoles(value = {"超级管理员","普通管理员"},logical = Logical.OR)
    public ResultVo pushMsg(String msg){
        try {
            WebSocketServer.BroadCastInfo(msg);
            return Result.success("成功");
        } catch (IOException e) {
            log.error("推送消息 {} 失败",msg);
            return Result.fail(ReturnCode.PUSH_ERROR);
        }
    }

}
