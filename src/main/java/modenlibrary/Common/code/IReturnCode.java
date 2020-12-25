package modenlibrary.Common.code;

/**
 * 错误代码的接口类
 *
 * @author L.star
 * @date 2020/12/22 11:29
 */
public interface IReturnCode {
    /**
     * 得到代码
     *
     * @return Integer
     */
    Integer getCode();

    /**
     * 得到消息
     * @return String
     */
    String getMsg();

}
