package modenlibrary.aop;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.anno.Operation;
import modenlibrary.entity.SysLog;
import modenlibrary.entity.User;
import modenlibrary.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * 记录日志的切面
 * @author L.star
 * @date 2020/12/26 15:30
 */
@Component
@Slf4j
@Aspect
public class LogAspect {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 在有注解的地方切入代码
     */
    @Pointcut("@annotation(modenlibrary.anno.Operation)")
    public void logPointCut(){}

    @AfterReturning("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint){
        SysLog sysLog = new SysLog();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null) {
            String value = operation.value();
            sysLog.setOper(value);//保存获取的操作
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();

        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = null;
        try {
            params = JSONUtil.toJsonStr(args);
        } catch (Exception e) {
            log.error("AOP-------------- Json出错");
            e.printStackTrace();
        }
        sysLog.setParams(params);

        //请求的时间
        sysLog.setCreatedate(DateUtil.date());

        //获取用户名
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        User user = (User) session.getAttribute("userInfo");
        sysLog.setUid(user.getId());

        //获取请求的ip地址
        String ipaddr = getClientIP(attr.getRequest());
        sysLog.setIp(ipaddr);

        sysLogService.insert(sysLog);
    }

    /***
     * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP,
     * @param request
     * @return
     */
    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
