package modenlibrary.Common.exception;

import modenlibrary.Common.code.IReturnCode;

/**
 * 自定义业务异常
 * @author L.star
 * @date 2020/12/22 12:17
 */
public class BusinessException extends RuntimeException {
    private int code;
    private String detailMessage;

    public BusinessException(int code,String message){
        super(message);
        this.code = code;
        this.detailMessage = message;
    }

    public BusinessException(IReturnCode code){
        this(code.getCode(),code.getMsg());
    }

    public int getCode(){
        return code;
    }

    public String getDetailMessage(){
        return detailMessage;
    }

}
