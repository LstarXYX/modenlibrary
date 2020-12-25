package modenlibrary.Common.exception;

import modenlibrary.Common.vo.ResultVo;
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
    @ExceptionHandler(value = BusinessException.class)
    public ResultVo businessException(BusinessException e){
        logger.error(e.getDetailMessage());
        return new ResultVo(e.getCode(),e.getDetailMessage());
    }

}
