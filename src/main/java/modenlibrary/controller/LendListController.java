package modenlibrary.controller;

import modenlibrary.Common.utils.PageUtils;
import modenlibrary.Common.vo.PageRequest;
import modenlibrary.Common.vo.PageResult;
import modenlibrary.entity.LendList;
import modenlibrary.service.LendListService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author L.star
 * @date 2020/12/24 19:49
 */
@Controller
@RequestMapping("/lendlist")
@CrossOrigin(originPatterns = "*",maxAge = 3600)
public class LendListController {

    @Autowired
    private LendListService lendListService;

    /**
     * 查询所有借书列表
     *
     * @param pageRequest
     * @return
     */
    @GetMapping("/queryall")
    @ResponseBody
    @RequiresPermissions("queryUserLendInfo")
    public PageResult queryAll(PageRequest pageRequest, LendList lendList){
        return PageUtils.getPageResult(pageRequest,lendListService.queryAll(pageRequest,lendList));
    }

}
