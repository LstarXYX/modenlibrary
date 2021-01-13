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
import modenlibrary.entity.Category;
import modenlibrary.service.DataService;
import modenlibrary.service.SysLogService;
import modenlibrary.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 统计数据的Controller
 * @author L.star
 * @date 2020/12/26 10:05
 */
@Controller
@RequestMapping("/data")
@Slf4j
@CrossOrigin(originPatterns = "*",maxAge = 3600)
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
     * 查询某一月份所有的借书人数
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
     * 查询本月份所有的借书人数
     *
     * @return
     */
    @GetMapping("/lendbooknum/month/thismonth")
    @ResponseBody
    @RequiresRoles(value = {"普通管理员","超级管理员"},logical = Logical.OR)
    public ResultVo lendNumOfThisMonth(){
        String YearMonth = DateUtil.thisYear()+"-"+DateUtil.thisMonth()+1;
        return Result.success(redisUtil.sGet(YearMonth));
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
    public ResultVo syslogAll(@RequestParam(value = "num",defaultValue = "100",required = false)Integer num){
        return Result.success(sysLogService.queryAll(num));
    }

    @GetMapping("/lendbooknum/today")
    @ResponseBody
    @RequiresRoles(value = {"超级管理员","普通管理员"},logical = Logical.OR)
    public ResultVo todayNum(){
        return Result.success(redisUtil.hget("LendBookNum"+DateUtil.thisMonth(),String.valueOf(DateUtil.thisDayOfMonth())));
    }

    /**
     * 获取不同类别图书的数量
     *
     * @return
     */
    @GetMapping("/book/categoryNum")
    @ResponseBody
    @RequiresRoles(value = {"超级管理员","普通管理员"},logical = Logical.OR)
    public ResultVo categoryNum(){
        return Result.success(dataService.categoryNum());
    }

    /**
     * 不同类型书本的借书人数
     *
     * @return
     */
    @GetMapping("/book/categoryLendNum")
    @ResponseBody
    @RequiresRoles(value = {"超级管理员","普通管理员"},logical = Logical.OR)
    public ResultVo categoryLendNum(){
        return Result.success(dataService.categoryLendNum());
    }

    /**
     * 男女生借书人数情况
     *
     * @return
     */
    @GetMapping("/lendbooknum/gender")
    @ResponseBody
    @RequiresRoles(value = {"超级管理员","普通管理员"},logical = Logical.OR)
    public ResultVo lendbooknumgender(){
        return Result.success(dataService.LendBookNumOfGender());
    }

    /**
     * 用户数量
     */
    @GetMapping("/usernum")
    @ResponseBody
    @RequiresRoles(value = {"超级管理员","普通管理员"},logical = Logical.OR)
    public ResultVo usernum(){
        return Result.success(dataService.getUserNum());
    }

    /**
     * 后台主页返回数据 一次传输
     * 不用请求多个接口
     *
     * @return
     */
    @GetMapping("/main")
    @ResponseBody
    @RequiresRoles(value = {"超级管理员","普通管理员"},logical = Logical.OR)
    public ResultVo mainIndex(){
//        存放返回数据
        Map<String,Object>res = new HashMap<>();
        //获取用户人数
        res.put("userNum",dataService.getUserNum());
        //本月借书人数
        res.put("lendBookNumThisMonth",dataService.LendBookNumOfMonth(DateUtil.thisYear()+"-"+DateUtil.thisMonth()+1));
        //男女生借书人数
        res.put("genderlist",dataService.LendBookNumOfGender());
        //不同书本类别图书数据
        res.put("categoryNum",dataService.categoryNum());
        //当天借书人数
        res.put("lendBookNumToday",redisUtil.hget("LendBookNum"+DateUtil.thisMonth(),String.valueOf(DateUtil.thisDayOfMonth())));
        //近五个月的借书人数 yyyy-mm
        String from,to;
        //到这个月的
        to = DateUtil.thisYear()+"-"+DateUtil.thisMonth()+1;
        int mon = DateUtil.thisMonth()+1;
        int yea = DateUtil.thisYear();
        mon -= 5;
        if (mon<=0){
            mon = mon==0?12:12+mon;
            yea -= 1;
        }
        if ((mon+"").length()==1){
            from = yea+"-0"+mon;
        }else{
            from = yea+"-"+mon;
        }
        res.put("rangeMonth",dataService.LendBookNumOfRangeMonth(from,to));
        return Result.success(res);
    }


}
