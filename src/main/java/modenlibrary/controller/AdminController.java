package modenlibrary.controller;

import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.eum.RoleEnum;
import modenlibrary.Common.utils.Result;
import modenlibrary.Common.vo.ResultVo;
import modenlibrary.anno.Operation;
import modenlibrary.service.UserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 超级管理员api
 * @author L.star
 * @date 2020/12/27 21:20
 */
@Controller
@RequestMapping("/admin")
@CrossOrigin(originPatterns = "*",maxAge = 3600)
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
        int ok = userService.changeRole(id, RoleEnum.ADMIN.getRoleid());
        if (ok==1){
            return Result.success("ok");
        }else {
            return Result.fail(ReturnCode.SYSTEM_ERROR);
        }
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
        int ok = userService.changeRole(id,RoleEnum.READER.getRoleid());
        if (ok==1){
            return Result.success("ok");
        }else {
            return Result.fail(ReturnCode.SYSTEM_ERROR);
        }
    }

}
