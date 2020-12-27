package modenlibrary.Common.exception;

import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.utils.Result;
import modenlibrary.Common.vo.ResultVo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @author L.star
 * @date 2020/12/22 12:21
 */
@ControllerAdvice
@ResponseBody
public class GlobalException {

    private static Logger logger = LoggerFactory.getLogger(GlobalException.class);

    /**
     * 业务异常拦截
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResultVo businessException(BusinessException e){
        logger.error(e.getDetailMessage());
        return new ResultVo(e.getCode(),e.getDetailMessage());
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public ResultVo handleshiroException(Exception e){
        return Result.fail(ReturnCode.AUTHOR_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public ResultVo AuthorizationException(Exception ex) {
        return Result.fail(ReturnCode.AUTHOR_ERROR);
    }

}
