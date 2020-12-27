package modenlibrary.Common.code;

/**
 *
 *
 * @author L.star
 * @date 2020/12/22 11:33
 */
public enum ReturnCode implements IReturnCode{

    /**
     * 相关返回信息的定义
     */
    SUCCESS(200, "成功"),
    LOGIN_ERROR(201, "用户名或密码错误"),
    UNAUTH(202,"用户未登录"),
    AUTHOR_ERROR(403, "没有权限"),
    FORM_ERROR(400, "表单错误"),
    BOOK_LESS(203,"该书已借完"),
    BOOK_UNKNOWN(204,"该书不存在"),
    CAN_NOT_LEND(205,"不能借书"),
    NOT_USER(301,"查无此人"),
    NOT_LEND_LIST(302,"没有此借书记录"),
    IMG_TYPE_ERROR(303,"图片格式错误"),
    IMG_BLANK_ERROR(304,"图片为空错误"),
    IMG_TOO_BIG(305,"图片大小错误"),
    REDIS_DELTA_ERROR(500,"递增因子错误"),
    REDIS_ERROR(501,"Redis错误"),
    SYSTEM_ERROR(999, "系统出错");

    private Integer code;
    private String msg;

    ReturnCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
