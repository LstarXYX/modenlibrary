package modenlibrary.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.Common.utils.RedisUtil;
import modenlibrary.Common.utils.Result;
import modenlibrary.Common.vo.ResultVo;
import modenlibrary.service.DataService;
import modenlibrary.service.SysLogService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 统计数据的Controller
 * @author L.star
 * @date 2020/12/26 10:05
 */
@Controller
@RequestMapping("/data")
@Slf4j
public class DataController {

    @Autowired
    private DataService dataService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysLogService sysLogService;

    private static final String KEY_LENDBOOKNUM = "LendBookNum";


    /**
     * 查询某一天的借阅人数 2020-12-10
     *
     * @param date
     * @return
     */
    @GetMapping("/lendbooknum/day/{date}")
    @ResponseBody
    @RequiresRoles(value = {"普通管理员","超级管理员"},logical = Logical.OR)
    public ResultVo lendNumOfDay(@PathVariable("date")String date){
        if (date==null){
            date = DateUtil.today();
        }
        //如果是今天 应该从redis找
        if (DateUtil.isSameDay(DateUtil.parse(date).toJdkDate(),DateUtil.parse(DateUtil.today()))){
                Object obj = redisUtil.hget(KEY_LENDBOOKNUM+DateUtil.thisMonth(),String.valueOf(DateUtil.thisDayOfMonth()));
                if (obj!=null){
                    int num = (int)obj;
                    return Result.success(num);
                }
                return Result.success(null);
        }
        return Result.success(dataService.LendBookNumOfDay(date));
    }

    /**
     * 查询某年某一月份每天借书人数的详细信息
     *
     * @param YearMonth YYYY-mm
     * @return
     */
    @GetMapping("/lendbooknum/Allday/{YearMonth}")
    @ResponseBody
    @RequiresRoles(value = {"普通管理员","超级管理员"},logical = Logical.OR)
    public ResultVo lendNumOfAllDay(@PathVariable("YearMonth")String YearMonth){
        if (YearMonth==null){
            YearMonth = DateUtil.thisYear()+"-"+DateUtil.thisMonth()+1;
        }
        return Result.success(dataService.LendBookNumOfAllDay(YearMonth));
    }

    /**
     * 查询某一月份所有的借书人数 默认本月
     *
     * @param YearMonth
     * @return
     */
    @GetMapping("/lendbooknum/month/{YearMonth}")
    @ResponseBody
    @RequiresRoles(value = {"普通管理员","超级管理员"},logical = Logical.OR)
    public ResultVo lendNumOfMonth(@PathVariable("YearMonth")String YearMonth){
        if (StrUtil.isBlank(YearMonth)){
            YearMonth = DateUtil.thisYear()+"-"+DateUtil.thisMonth()+1;
        }
        return Result.success(dataService.LendBookNumOfMonth(YearMonth));
    }

    /**
     * 从某年某月到某年某月的借阅人数列表
     *
     * @param from  某年某月
     * @param to    某年某月
     * @return
     */
    @GetMapping("/lendbooknum/rangemonth")
    @ResponseBody
    @RequiresRoles(value = {"普通管理员","超级管理员"},logical = Logical.OR)
    public ResultVo lendNumOfRangeMonth(String from,String to){
        //如果是空的 返回这个月的给它
        if (StrUtil.isBlank(from)||StrUtil.isBlank(to)){
            return Result.success(dataService.LendBookNumOfMonth(DateUtil.thisYear()+"-"+DateUtil.thisMonth()+1));
        }
        return Result.success(dataService.LendBookNumOfRangeMonth(from, to));
    }

    /**
     * 某个ip最新30条操作记录
     *
     * @param ip
     * @return
     */
    @GetMapping("/syslog/ip/{ip}")
    @ResponseBody
    @RequiresRoles("超级管理员")
    public ResultVo syslogByIp(@PathVariable("ip")String ip){
        return Result.success(sysLogService.queryByIp(ip));
    }

    /**
     * 查询所有操作记录 100条
     *
     * @return
     */
    @GetMapping("/syslog/all")
    @ResponseBody
    @RequiresRoles("超级管理员")
    public ResultVo syslogAll(){
        return Result.success(sysLogService.queryAll());
    }


}
