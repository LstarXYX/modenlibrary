package modenlibrary.Common.vo;

import modenlibrary.Common.code.IReturnCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回格式
 *
 * @author L.star
 * @date 2020/12/22 11:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo<T> {

    //代码
    private Integer code;
    //消息
    private String msg;
    //对应返回的数据
    private T data;

    public ResultVo(Integer code, String msg) {
        setCode(code);
        setMsg(msg);
    }

    public ResultVo(IReturnCode code, T data) {
        setCodeMessage(code);
        setData(data);
    }

    public ResultVo setCodeMessage(IReturnCode codeMessage){
        setCode(codeMessage.getCode());
        setMsg(codeMessage.getMsg());
        return this;
    }



}
