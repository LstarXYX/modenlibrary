package modenlibrary.Common.utils;

import modenlibrary.Common.code.IReturnCode;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.vo.ResultVo;

/**
 * 返回对象的工具类
 * @author L.star
 * @date 2020/12/22 11:43
 */
public class Result {

    /**
     * 成功返回对象
     * @param data
     * @return ResultVo
     */
    public static ResultVo success(Object data){
        return new ResultVo(ReturnCode.SUCCESS,data);
    }

    /**
     * 失败返回对象
     * @param returnCode
     * @return ResultVo
     */
    public static ResultVo fail(IReturnCode returnCode){
        return new ResultVo().setCodeMessage(returnCode);
    }

    /**
     * 自定义返回对象
     *
     * @param code
     * @param data
     * @return ResultVo
     */
    public static ResultVo result(IReturnCode code,Object data){
        return new ResultVo(code,data);
    }

}
